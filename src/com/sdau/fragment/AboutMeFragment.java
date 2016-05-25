package com.sdau.fragment;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import com.sdau.superlibrary.R;
import com.sdau.activity.MainActivity;
import com.sdau.activity.NewsInfoActivity;
import com.sdau.activity.UserLoginActivity;
import com.sdau.adapter.BookListViewAdapter;
import com.sdau.adapter.BookMenuListAdapter;
import com.sdau.bean.BookItemBean;
import com.sdau.httpclient.PersistentCookieStore;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutMeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    private Button btn1;
    private ImageView img1;
    private ImageView user_logo;
    private TextView tv1;
    private ListView listView;
	List<BookItemBean> dataList = null;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutMeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutMeFragment newInstance(String param1, String param2) {
        AboutMeFragment fragment = new AboutMeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AboutMeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View aboutMeLayout = inflater.inflate(R.layout.fragment_about_me, container, false);
    	listView = (ListView)aboutMeLayout.findViewById(R.id.lv_mybookmenu);
    	dataList=new ArrayList<BookItemBean>();
    	BookItemBean item=new BookItemBean();
    	item.bookname="小王子";
    	item.author="(法)安托万·德·圣埃克苏佩里著 ";
    	item.booknum="馆藏复本：6 可借复本：1";
    	dataList.add(item);
    	item=new BookItemBean();
    	item.bookname="Android开发实例大全 ";
    	item.author="王东华等编著 ";
    	item.booknum="馆藏复本：3 可借复本：0";
    	dataList.add(item);
    	item=new BookItemBean();
    	item.bookname="追风筝的人 ";
    	item.author="(美) 卡勒德·胡赛尼著";
    	item.booknum="馆藏复本：9 可借复本：1";
    	dataList.add(item);
    	listView.setAdapter(new BookMenuListAdapter(getActivity(), dataList));
    	//user_logo=(ImageView)aboutMeLayout.findViewById(R.id.user_logo_img);
    	/*user_logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getContext(), UserLoginActivity.class);
				startActivity(intent);
				
			}
		});*/
    	/*btn1=(Button)aboutMeLayout.findViewById(R.id.button1);
        img1=(ImageView)aboutMeLayout.findViewById(R.id.imageView1);
        tv1=(TextView)aboutMeLayout.findViewById(R.id.textView1);
        btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OkHttpClient client = new OkHttpClient();  
				client.setCookieHandler(new CookieManager(new PersistentCookieStore((MainActivity) getActivity()), CookiePolicy.ACCEPT_ALL));  
				Request request = new Request.Builder().url("http://202.194.143.19/reader/login.php").build();  
				try {  
				    Response response = client.newCall(request).execute();  
				    if(response.isSuccessful()){  
				    	tv1.setText(response.body().string());
				        //return Jsoup.parse(response.body().string());  
				    }  
				} catch (IOException e) {  
				    e.printStackTrace();  
				}  
			}
		});*/
        return aboutMeLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
