package sdwxwx.com.letter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import sdwxwx.com.R;
import sdwxwx.com.letter.activity.LetterChatActivity;
import sdwxwx.com.letter.bean.LetterBean;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgComingItemDelagate implements ItemViewDelegate<LetterBean>
{
    public MsgComingItemDelagate(Context context) {
        this.context = context;
    }

    public Context context;

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.me_chat_item;
    }

    @Override
    public boolean isForViewType(LetterBean item, int position)
    {
        return item.isComMeg();
    }
    @Override
    public void convert(ViewHolder holder, LetterBean chatMessage, int position)
    {
        holder.setText(R.id.me_content, chatMessage.getContent());
        Glide.with(context).load(chatMessage.getIcon()).into((ImageView)holder.getView(R.id.me_head_pic));

        holder.setOnClickListener(R.id.me_head_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LetterChatActivity.class);
                context.startActivity(intent);
            }
        });


    }
}
