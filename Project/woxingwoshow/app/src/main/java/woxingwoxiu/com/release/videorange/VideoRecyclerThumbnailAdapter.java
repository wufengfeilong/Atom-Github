package woxingwoxiu.com.release.videorange;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksyun.media.shortvideo.kit.KSYEditKit;

import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;

/**
 * video thumbnail adapter
 */
public class VideoRecyclerThumbnailAdapter extends BaseAdapter<VideoThumbnailInfo> {

    private List<VideoThumbnailInfo> mList;
    private Context mContext;
    private KSYEditKit mRetriever;
    private View mNext;
    private Bitmap mDefaultBmp;

    public VideoRecyclerThumbnailAdapter(Context context, List<VideoThumbnailInfo> values, KSYEditKit
            retriever) {
        super(R.layout.item_thumbnail, values);
        mList = values;
        mContext = context;
        mRetriever = retriever;
        mNext = null;
        mDefaultBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_default);
    }

    @Override
    protected void convert(BaseHolder holder, VideoThumbnailInfo item) {
        super.convert(holder, item);
        ImageView thumbnailImg = holder.getView(R.id.thumbnail);
        int position = holder.getPosition();
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