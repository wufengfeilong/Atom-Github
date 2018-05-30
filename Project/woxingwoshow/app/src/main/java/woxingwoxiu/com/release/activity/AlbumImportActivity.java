package woxingwoxiu.com.release.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.release.adapter.MediaGalleryAdapter;
import woxingwoxiu.com.release.adapter.MediaSelectedAdapter;
import woxingwoxiu.com.release.media.MediaInfo;
import woxingwoxiu.com.release.media.MediaStorage;
import woxingwoxiu.com.release.media.ThumbnailGenerator;

public class AlbumImportActivity extends Activity {

    private static final int RESULT_CODE = -1;

    private ImageButton mBack;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_album_import);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mObserver = new ButtonObserver();
        mBack = (ImageButton) findViewById(R.id.gallery_backBtn);
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
//                mSelectedAdapter.addData(info);
                Intent intent = new Intent(AlbumImportActivity.this, AlbumEditActivity.class);
                intent.putExtra(Constant.COMPOSE_PATH, info.filePath);
//                intent.putExtra(Constant.PREVIEW_LEN, info.duration);
//                startActivityForResult(intent, ALBUM_EDIT_REQUEST_CODE);
                startActivity(intent);
//                setResult(RESULT_CODE, intent);
//                finish();
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

//    private void initMediaSelectedView() {
//        mSelectedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mSelectedAdapter = new MediaSelectedAdapter(mThumbGenerator);
//        mSelectedAdapter.setItemCallback(new MediaSelectedAdapter.ItemCallback() {
//            @Override
//            public void onDurationUpdate(long duration) {
//                int sec = Math.round(((float) duration) / 1000);
//                int hour = sec / 3600;
//                int min = (sec % 3600) / 60;
//                sec = (sec % 60);
//                mTotalDuration.setText(String.format("%1$02d:%2$02d:%3$02d", hour, min, sec));
//            }
//        });
//        mSelectedView.setAdapter(mSelectedAdapter);
//    }

//    private void onSelectedDataConfirmed() {
//        List<MediaInfo> list = mSelectedAdapter.getSelectedList();
//        ArrayList<String> path = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            path.add(list.get(i).filePath);
//        }
//        if (path.size() == 0) {
//            Toast.makeText(this, "请至少选择一个文件", Toast.LENGTH_SHORT).show();
//        } else {
//            Intent intent = new Intent();
//            intent.putStringArrayListExtra("filePath", path);
//            setResult(RESULT_CODE, intent);
//            finish();
//        }
//    }

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
