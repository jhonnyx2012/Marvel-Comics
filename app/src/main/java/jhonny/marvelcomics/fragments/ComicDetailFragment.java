package jhonny.marvelcomics.fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.Locale;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.adapters.ComicAdapter;
import jhonny.marvelcomics.adapters.ResourceAdapter;
import jhonny.marvelcomics.managers.ComicManager;
import jhonny.marvelcomics.models.Comic;
import jhonny.marvelcomics.models.Resource;
import jhonny.marvelcomics.utils.Utils;
import jhonny.marvelcomics.utils.ViewUtils;

public class ComicDetailFragment extends BaseFragment {

    private Comic comic;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView ivThumbnail;
    private TextView tvPrice,tvDate,tvPages,tvDescription, tvSeriesCount,tvCreatorsCount,tvCharactersCount;
    private Button bOpenWebDetail;
    private RecyclerView rvSeries,rvCreators,rvCharacters;
    private ProgressBar progressBar;
    private ComicAdapter adapter;
    private int position;

    public static Fragment newInstance(ComicAdapter adapter, int position) {
        ComicDetailFragment fragment = new ComicDetailFragment();
        fragment.setComic(adapter.getItem(position));
        fragment.setAdapter(adapter);
        fragment.setPosition(position);
        return fragment;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_comic_detail;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;
        collapsingToolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);
        ivThumbnail= (ImageView) view.findViewById(R.id.ivThumbnail);
        tvPrice= (TextView) view.findViewById(R.id.tvPrice);
        tvDate= (TextView) view.findViewById(R.id.tvDate);
        tvPages= (TextView) view.findViewById(R.id.tvPages);
        tvDescription= (TextView) view.findViewById(R.id.tvDescription);
        bOpenWebDetail= (Button) view.findViewById(R.id.bOpenWebDetail);
        rvSeries= (RecyclerView) view.findViewById(R.id.rvSeries);
        rvCreators= (RecyclerView) view.findViewById(R.id.rvCreators);
        rvCharacters= (RecyclerView) view.findViewById(R.id.rvCharacters);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        tvSeriesCount= (TextView) view.findViewById(R.id.tvSeriesCount);
        tvCreatorsCount= (TextView) view.findViewById(R.id.tvCreatorsCount);
        tvCharactersCount= (TextView) view.findViewById(R.id.tvCharactersCount);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }});
        refreshData();
        return view;
    }

    private void refreshData() {
        if(collapsingToolbarLayout==null||comic==null||activity==null)return;
        setTitle(getTitle());
        Glide.with(activity)
                .load(comic.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(ViewUtils.getGlideListener(progressBar))
                .into(ivThumbnail);
        updateFavIcon();
        tvPrice.setText(comic.getPrice(activity));
        ViewUtils.fillOrHideTextView(tvDate,comic.getDate());
        ViewUtils.fillOrHideTextView(tvPages, String.format(getString(R.string.pages),comic.getPageCount()));
        ViewUtils.fillOrHideTextView(tvDescription,comic.getDescription());
        if(ViewUtils.showOrHideView(bOpenWebDetail,comic.getWebUrl()))
        {
            bOpenWebDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.openURL(comic.getWebUrl(),activity);
                }
            });
        }
        initResouceList(rvSeries,comic.getArraySeries());
        initResouceList(rvCharacters,comic.getArrayCharacters());
        initResouceList(rvCreators,comic.getArrayCreators());
        tvSeriesCount.setText(String.format(Locale.getDefault(),"%1$d",comic.getArraySeries().size()));
        tvCreatorsCount.setText(String.format(Locale.getDefault(),"%1$d",comic.getArrayCreators().size()));
        tvCharactersCount.setText(String.format(Locale.getDefault(),"%1$d",comic.getArrayCharacters().size()));
    }

    private void initResouceList(RecyclerView list, ArrayList<Resource> arrayData) {
        ResourceAdapter adapter=new ResourceAdapter(arrayData);
        list.setLayoutManager(new LinearLayoutManager(activity));
        list.setAdapter(adapter);
    }

    private void updateFavIcon() {
        if(getMenuObject()==null)return;
        MenuItem favItem=getMenuObject().findItem(R.id.toggle_fav);
        if(favItem!=null)
            favItem.setIcon(ViewUtils.getTintedDrawable(
                    activity,
                    R.drawable.ic_star_white_24dp,
                    comic.isFavorite()?R.color.yellow_fav:R.color.white));
    }

    @Override
    protected boolean haveToolbar() {
        return true;
    }

    @Override
    protected int getTitle() {
        return R.string.empty_string;
    }

    @Override
    public void setTitle(int id) {
        if(collapsingToolbarLayout!=null)
            collapsingToolbarLayout.setTitle(comic.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.toggle_fav:
                ComicManager.addOrRemove(comic);
                updateFavIcon();
                adapter.notifyItemChanged(position);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateFavIcon();
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public void setAdapter(ComicAdapter adapter) {
        this.adapter = adapter;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
