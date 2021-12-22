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
        viewModel.getUiState().observe(this, state -> {
            switch (state){
                case CONTENT:
                    break;
                case ERROR:
                    break;
                case EMPTY:
                    EmptyView emptyView = getEmptyView();
                    emptyView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(emptyView);
                    break;
                case LOADING:
                    binderAdapter.setEmptyView(R.layout.view_loading);
                    break;
                case NETWORK:
                    NetworkView networkView = getNetworkView();
                    networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
                    binderAdapter.setEmptyView(networkView);
                    break;
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
                case MORE_END:
                    binderAdapter.getLoadMoreModule().loadMoreEnd();
                    break;
                case MORE_ERROR:
                    binderAdapter.getLoadMoreModule().loadMoreFail();
                    break;
                case MORE_FINISH:
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
        EmptyView emptyView = new EmptyView(requireContext());
        emptyView.setMessage("没有发现数据");
        emptyView.setHint("重新点击试试");
        return emptyView;
    }

    @NonNull
    public NetworkView getNetworkView() {
        NetworkView networkView = new NetworkView(requireContext());
        networkView.setMessage("当前网络异常");
        networkView.setHint("重新点击试试");
        networkView.setOnRetryListener(() -> viewModel.onLazyLoad());
        return networkView;
    }
}