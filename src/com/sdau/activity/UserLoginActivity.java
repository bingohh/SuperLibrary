package com.sdau.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sdau.superlibrary.R;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginActivity extends Activity {

	private EditText et_account;
	private EditText et_password;
	private EditText et_checkcode;
	private Button btn_login;
	private ImageView img_checkCode;
	private TextView tv_msg;
	private ImgAsyncTask asyncTask;

	private SharedPreferences sp;
	
	private Context lActivity;
	//private static OkHttpClient mOkHttpClient;
	
	private String COOKIE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		lActivity=this;
		// 验证码
		img_checkCode = (ImageView) findViewById(R.id.img_checkcode);
		tv_msg = (TextView) findViewById(R.id.tv_testInfo);
		asyncTask = new ImgAsyncTask();
		asyncTask.execute();
		// 验证码刷新
		img_checkCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				asyncTask = new ImgAsyncTask();
				asyncTask.execute();
			}
		});

		// 完成sp的初始化。
		sp = getSharedPreferences("config", MODE_PRIVATE);
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		et_checkcode = (EditText) findViewById(R.id.et_checkcode);
		// 获取sp里面存储的数据
		String savedAccount = sp.getString("account", "");
		String savedPassword = sp.getString("password", "");
		et_account.setText(savedAccount);
		et_password.setText(savedPassword);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String account = et_account.getText().toString();
				String password = et_password.getText().toString();
				String checkcode=et_checkcode.getText().toString();
				if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(checkcode)) {
					tv_msg.setText("帐号、密码或者验证码不能为空");
					Toast.makeText(getApplicationContext(), "帐号、密码或者验证码不能为空", Toast.LENGTH_LONG).show();
				}else{
					// 检查用户是否勾选了 记住密码的选项。
					// 说明勾选框被选中了。把用户名和密码给记录下来
					// 获取到一个参数文件的编辑器。
					Editor editor = sp.edit();
					editor.putString("account", account);
					editor.putString("password", password);
					// 把数据给保存到sp里面
					editor.commit();
					//Toast.makeText(getApplicationContext(), "用户信息已经保存", 1).show();
					Log.d("TAG", "account:"+account);
					Log.d("TAG", "password:"+password);
					Log.d("TAG", "checkcode:"+checkcode);
					RequestBody formBody = new FormEncodingBuilder()
						      .add("number", account)
						      .add("passwd", password)
						      .add("captcha", checkcode)
						      .add("select", "cert_no")
						      .build();
					LoginAsyncTask lac=new LoginAsyncTask();
					lac.execute(formBody);
				}
			}
		});

	}
	
	class LoginAsyncTask extends AsyncTask<RequestBody, Integer, String> {
		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected String doInBackground(RequestBody... params) {
			OkHttpClient client = new OkHttpClient();
			/*client.setCookieHandler(
					new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL));*/
			Log.d("TAG", "COOKIE:"+COOKIE);
			Request request = new Request.Builder().url("http://202.194.143.19/reader/redr_verify.php").header("Cookie", "PHPSESSID="+COOKIE).post(params[0]).build();
			try {
				Response response = client.newCall(request).execute();
				if (response.isSuccessful()) {
					return response.body().string();
				} else {
					return "failure";
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(String html) {
			
			tv_msg.setMovementMethod(ScrollingMovementMethod.getInstance());
			String username="";
			Document document = Jsoup.parse(html);
			username = document.select(".header_right_font font").text();
			//username=es.select("font").text();
			if(!TextUtils.isEmpty(username)){
				Intent intent = new Intent();
				Toast.makeText(lActivity, username+"同学，欢迎使用本软件~", Toast.LENGTH_LONG).show();
				intent.setClass(lActivity, MainActivity.class);
				startActivity(intent);
			}else{
				String alert =document.select("#left_tab font").text();
				Toast.makeText(lActivity, alert, Toast.LENGTH_LONG).show();
				tv_msg.setText(alert);
			}
			
		}

		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute() {
			tv_msg.setText("正在登录");
		}

		/**
		 * 这里的Intege参数对应AsyncTask中的第二个参数
		 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
		 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			//int vlaue = values[0];
		}

	}

	class ImgAsyncTask extends AsyncTask<String, Integer, Bitmap> {
		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://202.194.143.19/reader/captcha.php");
			HttpResponse httpResponse = null;
			try {
				httpResponse = client.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			COOKIE = ((AbstractHttpClient) client).getCookieStore().getCookies().get(0).getValue();
			Log.d("TAG", "COOKIE:"+COOKIE);
			byte[] bytes = null;
			try {
				bytes = EntityUtils.toByteArray(httpResponse.getEntity());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
			return bitmap; 
			/*try {
				return BitmapFactory.decodeStream(new URL("http://202.194.143.19/reader/captcha.php").openStream());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}*/
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			img_checkCode.setImageBitmap(result);
			img_checkCode.setScaleType(ScaleType.CENTER_CROP);
		}

		/**
		 * 获取网落图片资源
		 * 
		 * @param url
		 * @return
		 */
		public Bitmap getHttpBitmap(String url) {
			URL myFileURL;
			Bitmap bitmap = null;
			try {
				myFileURL = new URL(url);
				// 获得连接
				HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
				// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
				conn.setConnectTimeout(6000);
				// 连接设置获得数据流
				conn.setDoInput(true);
				// 不使用缓存
				conn.setUseCaches(false);
				// 这句可有可无，没有影响
				// conn.connect();
				// 得到数据流
				InputStream is = conn.getInputStream();
				// 解析得到图片
				bitmap = BitmapFactory.decodeStream(is);
				// 关闭数据流
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return bitmap;

		}

		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute() {
			tv_msg.setText("获取验证码");
		}

		/**
		 * 这里的Intege参数对应AsyncTask中的第二个参数
		 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
		 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			//int vlaue = values[0];
			// progressBar.setProgress(vlaue);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
