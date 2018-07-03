package sdwxwx.com.release.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.release.adapter.MediaGalleryAdapter;
import sdwxwx.com.release.media.MediaInfo;
import sdwxwx.com.release.media.MediaStorage;
import sdwxwx.com.release.media.ThumbnailGenerator;
import sdwxwx.com.release.utils.ViewUtils;

public class AlbumImportActivity extends Activity {

    private static final int RESULT_CODE = -1;

    private ImageView mBack;
    private TextView mTitle;
    private RecyclerView mGalleryView;
    private RelativeLayout mEmptyRL;
//    private TextView mTotalDuration;
//    private Button mNextStep;
//    private RecyclerView mSelectedView;

    private final int ALBUM_EDIT_REQUEST_CODE = 1100;

    private ButtonObserver mObserver;
    private MediaStorage mMediaStorage;
    private ThumbnailGenerator mThumbGenerator;
    private MediaGalleryAdapter mMediaGalleryAdapter;
//    private MediaSelectedAdapter mSelectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_import);
        // 沉浸式状态栏
        ViewUtils.setImgTransparent(this);

        mObserver = new ButtonObserver();
        mBack = findViewById(R.id.gallery_backBtn);
        mBack.setOnClickListener(mObserver);
        mTitle = (TextView) findViewById(R.id.gallery_title);
//        mTotalDuration = (TextView) findViewById(R.id.tv_duration_value);
//        mNextStep = (Button) findViewById(R.id.btn_next_step);
//        mNextStep.setOnClickListener(mObserver);
        mGalleryView = (RecyclerView) findViewById(R.id.gallery_media);
        mEmptyRL = (RelativeLayout) findViewById(R.id.album_empty_rl);
//        mSelectedView = (RecyclerView) findViewById(R.id.rv_selected_video);
        mMediaStorage = new MediaStorage(this);
        mThumbGenerator = new ThumbnailGenerator(this);
        initMediaGalleryView();
//        initMediaSelectedView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaStorage.cancelTask();
        mThumbGenerator.cancelAllTask();
    }

    private void initMediaGalleryView() {
        mGalleryView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        mMediaGalleryAdapter = new MediaGalleryAdapter(mMediaStorage, mThumbGenerator);
        mMediaGalleryAdapter.setOnItemClickListener(new MediaGalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MediaInfo info) {
                Intent intent = new Intent(AlbumImportActivity.this, AlbumEditActivity.class);
                intent.putExtra(Constant.COMPOSE_PATH, info.filePath);
                startActivity(intent);
            }
        });
        mMediaStorage.setOnMediaDataUpdateListener(new MediaStorage.OnMediaDataUpdateListener() {
            @Override
            public void onDateUpdate(List<MediaInfo> data) {
                int count = mMediaGalleryAdapter.getItemCount();
                int size = data.size();
                // 相册中没有视频
                if (size == 0 && count == 0) {
                    mGalleryView.setVisibility(View.GONE);
                    mEmptyRL.setVisibility(View.VISIBLE);
                }
                int insert = count - size;
                mMediaGalleryAdapter.notifyItemRangeInserted(insert, size);
            }
        });
        mGalleryView.setAdapter(mMediaGalleryAdapter);
        mMediaStorage.startCaptureMedias(MediaStorage.MEDIA_TYPE_VIDEO);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaStorage.cancelTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ALBUM_EDIT_REQUEST_CODE:
                if (data != null) {
                    setResult(RESULT_CODE, data);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private class ButtonObserver implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.gallery_backBtn:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
