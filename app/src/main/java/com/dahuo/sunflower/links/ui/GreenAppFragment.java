package com.dahuo.sunflower.links.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dahuo.sunflower.links.R;
import com.dahuo.sunflower.links.base.LazyFragment;
import com.dahuo.sunflower.links.common.Constants;
import com.dahuo.sunflower.links.common.Logger;
import com.dahuo.sunflower.links.model.AppsResponse;
import com.dahuo.sunflower.links.model.RecommendInfo;
import com.dahuo.sunflower.links.utils.AssetUtils;
import com.dahuo.sunflower.links.utils.Nav;
import com.dahuo.sunflower.links.utils.Resources;
import com.dahuo.sunflower.links.vm.GreenAppViewModel;
import com.github.captain_miao.uniqueadapter.library.OnClickPresenter;
import com.github.captain_miao.uniqueadapter.library.UniqueAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author YanLu
 * @since 17/10/20
 */

public class GreenAppFragment extends LazyFragment implements OnClickPresenter<GreenAppViewModel>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "GreenAppFragment";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    UniqueAdapter mAdapter;
    List<GreenAppViewModel> mData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Resources.getAttrColor(getActivity(), R.attr.colorAccent));
        initRecycleView(view);
        mAdapter.setOnClickPresenter(this);
    }

    @Override
    public void onVisibleFragment() {
        //showProgressDialog();
        loadData();
    }

    @Override
    public void onInvisibleFragment() {

    }

    private void initRecycleView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEmptyViewContainer = view.findViewById(R.id.empty_view_container);

        mAdapter = new UniqueAdapter(mData);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, null);
        setEmptyView(mEmptyView);

        mEmptyView.findViewById(R.id.tv_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

    }

    private void loadData() {
        onRefresh();
    }

    @Override
    public void onClick(View view, GreenAppViewModel itemModel) {
        if(itemModel.app.openWithGooglePlay) {
            Nav.openMarket(getActivity(), itemModel.app.packageName, itemModel.app.downloadUrl);
        } else {
            Nav.openWithBrowser(getActivity(), itemModel.app.downloadUrl);
        }
    }


    Call<AppsResponse> mCall;

    public void onRefresh() {
//        if ((mCall == null || mCall.isCanceled())) {
//            // 暂时没有接口
//            //mCall = XadApi.getRecommendApps(mApiCallback);
//        } else {
//            mApiCallback.onFinish();
//        }

        new AsyncTask<Boolean, Integer, List<RecommendInfo>>() {
            @Override
            protected List<RecommendInfo> doInBackground(Boolean... bool) {
                Type type = new TypeToken<List<RecommendInfo>>() {
                }.getType();

                return AssetUtils.getDataFromAsset(getActivity(), Constants.APPS_JSON, type);
            }

            @Override
            protected void onPostExecute(List<RecommendInfo> appList) {
                if(appList != null) {
                    mData.clear();
                    for (RecommendInfo rule : appList) {
                        GreenAppViewModel viewModel = new GreenAppViewModel(rule);
                        mData.add(viewModel);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            mSwipeRefreshLayout.setRefreshing(false);
            mRecyclerView.scrollToPosition(0);
//            dismissProgressDialog();

            }
        }.execute();


    }

//    final ApiCallback<AppsResponse> mApiCallback = new ApiCallback<AppsResponse>() {
//        @Override
//        public void onResponse(AppsResponse rulesResponse) {
//            if(rulesResponse == null || rulesResponse.data == null || rulesResponse.data.size() == 0) {
//                return;
//            }
//
//            mData.clear();
//            for(RecommendInfo rule : rulesResponse.data) {
//                RecommendAppViewModel viewModel = new RecommendAppViewModel(rule);
//                mData.add(viewModel);
//            }
//            mAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onFailure(ApiError error) {
//            Logger.e(TAG, error.toString());
//        }
//
//        @Override
//        public void onFinish() {
//            mCall = null;
//            mSwipeRefreshLayout.setRefreshing(false);
//            mRecyclerView.scrollToPosition(0);
//            dismissProgressDialog();
//        }
//
//    };







    // add empty view
    private View mEmptyView;
    private boolean mRegisterCheckEmptyView = false;
    private FrameLayout mEmptyViewContainer;
    final private RecyclerView.AdapterDataObserver mAdapterObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
          checkIfEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
          checkIfEmptyView();
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            checkIfEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
          checkIfEmptyView();
        }
      };

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;

        if(mEmptyViewContainer.getChildCount() > 0){
            Logger.e(TAG, "had empty view...maybe setEmptyView twice");
            mEmptyViewContainer.removeAllViews();
        }

        mEmptyViewContainer.addView(emptyView);

        registerAdapterDataObserver();
    }

    private void registerAdapterDataObserver(){
        if(!mRegisterCheckEmptyView && mEmptyView != null && mAdapter != null){
            mRegisterCheckEmptyView = true;
            mAdapter.registerAdapterDataObserver(mAdapterObserver);
        }
    }

    private void unregisterAdapterDataObserver(){
        if(mRegisterCheckEmptyView){
            mAdapter.registerAdapterDataObserver(mAdapterObserver);
            mRegisterCheckEmptyView = false;
        }
    }

    private void checkIfEmptyView() {
      if (mEmptyView != null && mAdapter != null) {
          if(mAdapter.getItemCount() == 0){
              mEmptyViewContainer.setVisibility(VISIBLE);
              mRecyclerView.setVisibility(GONE);
          } else {
              mRecyclerView.setVisibility(VISIBLE);
              mEmptyViewContainer.setVisibility(GONE);
          }
      }
    }
}
