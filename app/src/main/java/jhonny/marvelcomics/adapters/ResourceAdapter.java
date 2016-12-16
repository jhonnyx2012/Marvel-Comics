package jhonny.marvelcomics.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import java.util.ArrayList;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.models.Resource;
import jhonny.marvelcomics.utils.ViewUtils;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {
    private ArrayList<Resource> list;

    public ResourceAdapter(ArrayList<Resource> list) {
        this.list =list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_resource, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        Resource item = getItem(i);
        if(item==null)return;
        holder.tvName.setText(item.getName());
        ViewUtils.fillOrHideTextView(holder.tvRole,item.getRole());
        holder.ivIcon
                .setImageDrawable(TextDrawable.builder()
                .buildRound(String.valueOf(item.getName().charAt(0)).toUpperCase(), Color.DKGRAY));
    }

    private Resource getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView tvName,tvRole;
        ViewHolder(View view) {
            super(view);
            tvName=(TextView) view.findViewById(R.id.tvName);
            tvRole=(TextView) view.findViewById(R.id.tvRole);
            ivIcon=(ImageView) view.findViewById(R.id.ivIcon);
        }
    }
}