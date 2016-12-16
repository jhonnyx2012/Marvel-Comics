package jhonny.marvelcomics.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.managers.ComicManager;
import jhonny.marvelcomics.managers.PreferencesManager;
import jhonny.marvelcomics.models.Comic;
import jhonny.marvelcomics.utils.Constants;
import jhonny.marvelcomics.utils.ViewUtils;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private final AdapterView.OnItemClickListener listener;
    private boolean isListMode;
    private ArrayList<Comic> list;
    private Context context;


    public ComicAdapter(Context context, AdapterView.OnItemClickListener listener) {
        this.context=context;
        this.list = new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.isListMode = PreferencesManager.getMode(context) == Constants.LIST_MODE;
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(isListMode ?
                        R.layout.item_comic_list : R.layout.item_comic_grid,
                        viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final Comic item = getItem(i);
        holder.progressBar.setVisibility(View.VISIBLE);
        DrawableRequestBuilder<String> glideRequest = Glide.with(context)
                .load(item.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(ViewUtils.getGlideListener(holder.progressBar));
        if(isListMode)
            glideRequest.centerCrop();
        glideRequest.into(holder.ivThumbnail);
        holder.flFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComicManager.addOrRemove(item);
                updateIcon(holder.ivFav,item);
            }
        });
        updateIcon(holder.ivFav,item);
        holder.tvPrice.setText(item.getPrice(context));
        holder.tvTitle.setText(item.getTitle());
        holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null,view,i,0);
            }
        });
    }

    private void updateIcon(ImageView ivFav,Comic item) {
        ivFav.setImageDrawable(ViewUtils.getTintedDrawable(
                context,
                R.drawable.ic_star_white_24dp,
                item.isFavorite()?R.color.yellow_fav:R.color.white));
    }

    public Comic getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Comic> results) {
        list.clear();
        list.addAll(results);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail,ivFav;
        TextView tvPrice,tvTitle;
        View flFav,progressBar;
        ViewHolder(View view) {
            super(view);
            ivThumbnail= (ImageView) view.findViewById(R.id.ivThumbnail);
            ivFav= (ImageView) view.findViewById(R.id.ivFav);
            tvPrice=(TextView) view.findViewById(R.id.tvPrice);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            flFav=view.findViewById(R.id.flFav);
            progressBar=view.findViewById(R.id.progressBar);
        }
    }
}