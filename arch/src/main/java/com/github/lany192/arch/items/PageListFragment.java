package com.github.lany192.arch.items;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.arch.R;
import com.github.lany192.arch.databinding.FragmentPageBinding;
import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.view.EmptyView;
import com.github.lany192.arch.view.ErrorView;
import com.github.lany192.arch.view.NetworkView;
import com.github.lany192.utils.NetUtils;
import com.hjq.toast.ToastUtils;

import java.lang.reflect.ParameterizedType;

public abstract class PageListFragment<VM extends PageListViewModel>
        extends BindingFragment<FragmentPageBinding> {
    private final BinderAdapter binderAdapter = new BinderAdapter();
    protected VM viewModel;

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getSpanCount());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }

    public void register(ItemBinder binder) {
        binderAdapter.addItemBinder(binder.getTargetClass(), binder);
    }

    public int getSpanCount() {
        return 2;
    }

    public int getItemSpanSize(int viewType, int position) {
        return getSpanCount();
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @CallSuper
    @Override
    public void initView() {
        //获取ViewModel对象
        viewModel = getFragmentViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

        binderAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> getItemSpanSize(viewType, position));
        binderAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (NetUtils.isAvailable(requireContext())) {
                viewModel.onLoadMore();
            } else {
                ToastUtils.show("网络异常");
                binderAdapter.getLoadMoreModule().loadMoreFail();
//                showNetView();
            }
        });

        binding.recyclerView.setLayoutManager(getLayoutManager());
        binding.recyclerView.setAdapter(binderAdapter);
        if (binding.recyclerView.getItemDecorationCount() < 1 && getItemDecoration() != null) {
            binding.recyclerView.addItemDecoration(getItemDecoration());
        }
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (NetUtils.isAvailable(requireContext())) {
                viewModel.onRefresh();
            } else {
                binding.refreshLayout.finishRefresh();
                NetworkView networkView = getNetworkView();
                networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
                binderAdapter.setEmptyView(networkView);
            }
        });
        viewModel.getViewState().observe(this, state -> {
            switch (state) {
                case CONTENT:
                    break;
                case ERROR:
                    binderAdapter.setEmptyView(getErrorView());
                    break;
                case EMPTY:
                    binderAdapter.setEmptyView(getEmptyView());
                    break;
                case LOADING:
                    binderAdapter.setEmptyView(R.layout.view_loading);
                    break;
                case NETWORK:
                    binderAdapter.setEmptyView(getNetworkView());
                    break;
                case SHOW_LOADING_DIALOG:
                    showLoadingDialog();
                    break;
                case CANCEL_LOADING_DIALOG:
                    cancelLoadingDialog();
                    break;
            }
        });
        viewModel.getListState().observe(this, state -> {
            switch (state) {
                case STOP_REQUEST:
                    binding.refreshLayout.finishRefresh();
                    binderAdapter.getLoadMoreModule().loadMoreFail();
                    break;
                case REFRESHING:
                    break;
                case REFRESH_FINISH:
                    binding.refreshLayout.finishRefresh();
                    break;
                case MORE_LOADING:
                    break;
                case MORE_LOAD_END:
                    binderAdapter.getLoadMoreModule().loadMoreEnd();
                    break;
                case MORE_LOAD_ERROR:
                    binderAdapter.getLoadMoreModule().loadMoreFail();
                    break;
                case MORE_LOAD_FINISH:
                    binderAdapter.getLoadMoreModule().loadMoreComplete();
                    break;
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (ListUtils.isEmpty(data.getItems())) {
                if (NetUtils.isAvailable(requireContext())) {
                    EmptyView emptyView = getEmptyView();
                    emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(emptyView);
                } else {
                    NetworkView networkView = getNetworkView();
                    networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(networkView);
                }
            } else {
                binderAdapter.setNewInstance(data.getItems());
            }
        });
    }

    @NonNull
    public EmptyView getEmptyView() {
        EmptyView view = new EmptyView(requireContext());
        view.setMessage("没有发现数据");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public NetworkView getNetworkView() {
        NetworkView view = new NetworkView(requireContext());
        view.setMessage("当前网络异常");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }

    @NonNull
    public ErrorView getErrorView() {
        ErrorView view = new ErrorView(requireContext());
        view.setMessage("未知错误");
        view.setHint("重新点击试试");
        view.setOnRetryListener(() -> viewModel.onLazyLoad());
        return view;
    }
}