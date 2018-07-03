package sdwxwx.com.release.videorange;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksyun.media.shortvideo.kit.KSYEditKit;

import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;

/**
 * video thumbnail adapter
 */
public class VideoRecyclerThumbnailAdapter extends BaseAdapter<VideoThumbnailInfo> {

    private List<VideoThumbnailInfo> mList;
    private Context mContext;
    private KSYEditKit mRetriever;
    private View mNext;
    private Bitmap mDefaultBmp;
    private final int HEADER_VIEW = 1;
    private final int CONTENT_VIEW = 0;

    public VideoRecyclerThumbnailAdapter(Context context, List<VideoThumbnailInfo> values, KSYEditKit
            retriever) {
        super(R.layout.item_thumbnail, values);
        mList = values;
        mContext = context;
        mRetriever = retriever;
        mNext = null;
        mDefaultBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_header_image);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else if (position == mList.size() + 1) {
            return HEADER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_edit_thumbnail_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);
        }
        return new BaseHolder(view, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() + 2;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (HEADER_VIEW == holder.getItemViewType()) {
            // 首尾同处理
            View emptyView = holder.getView(R.id.header);
            ViewGroup.LayoutParams param = holder.itemView.getLayoutParams();
            param.width = emptyView.getWidth();
            param.height = emptyView.getHeight();
            holder.itemView.setLayoutParams(param);
        } else {
            super.onBindViewHolder(holder, position - 1);
        }
    }

    @Override
    protected void convert(BaseHolder holder, VideoThumbnailInfo item) {
        super.convert(holder, item);
        ImageView thumbnailImg = holder.getView(R.id.thumbnail);
        // 因为有Header所以 - 1
        int position = holder.getPosition() - 1;
        holder.setImageBitmap(R.id.thumbnail, mList.get(position).mBitmap);

        ViewGroup.LayoutParams param = holder.itemView.getLayoutParams();
        param.width = mList.get(position).mWidth;
        holder.itemView.setLayoutParams(param);

        if (mList.get(position).mBitmap != null) {
            thumbnailImg.setImageBitmap(mList.get(position).mBitmap);
        } else {
            if (mList.get(position).mType == VideoThumbnailInfo.TYPE_NORMAL) {
                VideoThumbnailTask.loadBitmap(mContext, thumbnailImg,
                        null, (long) (mList.get(position).mCurrentTime * 1000), mList.get(position),
                        mRetriever, mNext);
            }
        }
    }
}