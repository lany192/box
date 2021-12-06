package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.items.ItemDelegate;
import com.github.lany192.box.items.ItemsFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends ItemsFragment<ArticleViewModel> {

    @Override
    public List<ItemDelegate> getDelegates() {
        List<ItemDelegate> items=new ArrayList<>();
        items.add(new ArticleDelegate());
        return items;
    }

//    @NonNull
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        adapter.register(Article.class, new ArticleDelegate());
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}