package org.lee.android.doodles.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.lee.android.doodles.ApiClient;
import org.lee.android.doodles.AppContext;
import org.lee.android.doodles.AppFunction;
import org.lee.android.doodles.R;
import org.lee.android.doodles.bean.Doodle;
import org.lee.android.doodles.bean.DoodlePackage;
import org.lee.android.doodles.volley.FileUtils;
import org.lee.android.doodles.volley.HttpHandler;
import org.lee.android.test.DataGeter;
import org.lee.android.test.Tester;
import org.lee.android.util.Log;
import org.lee.android.util.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * 搜索Doodles页面
 */
public class SearchFragment extends Fragment implements
        View.OnTouchListener, RecyclerItemViewHolder.ViewHolderClicks {

    public static SearchFragment newInstance(String q) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("q", q);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, null, false);
    }

    private String mQ;
    private View progressContainer;
    private View listContainer;
    private TextView internalEmpty;
    private RecyclerView recyclerView;
    private DoodlePackage mDoodlePkg;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        progressContainer = view.findViewById(R.id.progressContainer);
        listContainer = view.findViewById(R.id.listContainer);
        internalEmpty = (TextView) view.findViewById(R.id.internalEmpty);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);

        mQ = getArguments().getString("q");

    }

    private void initRecyclerView(RecyclerView recyclerView, Doodle[] doodles) {
//        int paddingTop = Utils.getToolbarHeight(getActivity()) + Utils.getTabsHeight(getActivity());
//        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(
                getActivity(), doodles, this);
        recyclerView.setAdapter(recyclerAdapter);
//        recyclerView.setOnScrollListener(mOnScrollListener);
        recyclerView.setOnTouchListener(this);
    }

    private ApiClient apiClient;
    @Override
    public void onResume() {
        super.onResume();

        if(apiClient == null){
            apiClient = new ApiClient();

            mDoodlePkg = DataGeter.getSearchDoodles();
            initRecyclerView(recyclerView, mDoodlePkg.doodles);

            if(true)
            return ;

            apiClient.searchDoodles(mQ, 1, new HttpHandler<DoodlePackage>() {
                @Override
                public void onStart() {
                    progressContainer.setVisibility(View.VISIBLE);
                    listContainer.setVisibility(View.GONE);
                }

                @Override
                public void onFinish() {
                    progressContainer.setVisibility(View.GONE);
                    listContainer.setVisibility(View.VISIBLE);

                }

                @Override
                public void onSuccess(int i, Header[] headers, String s, DoodlePackage doodlePkg) {
                    if(doodlePkg != null){
                        Log.anchor(doodlePkg.results_number);
                        initRecyclerView(recyclerView, doodlePkg.doodles);
                    }else{
                        onFailure(0, "没有搜到匹配内容");
                    }
                }

                @Override
                public void onFailure(int statusCode, String error) {
                    Log.anchor("statusCode:" + statusCode + ", " + error);
                    internalEmpty.setText(error);

                }
            });

        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private boolean blockTouch;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                blockTouch = false;
                v.requestFocus();
                blockTouch = AppFunction.hideInputMethod(getActivity(), v);
                if (blockTouch) {
                    return blockTouch;
                }
                return blockTouch;
            case MotionEvent.ACTION_MOVE:
                return blockTouch;
        }
        return blockTouch;
    }

    @Override
    public void onItemClick(View parent, int position) {
        Toast.show("onItemClick");
        Doodle doodle = mDoodlePkg.doodles[position];
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.tabcontent,
                        DoodleDetailsFragment.newInstance(doodle,
                                (int) parent.getX(), (int) parent.getY(),
                                parent.getWidth(), parent.getHeight())
                )
                .addToBackStack("detail").commit();
    }

    @Override
    public void onSearch(TextView searchView) {
        Toast.show("onSearch");

    }

}