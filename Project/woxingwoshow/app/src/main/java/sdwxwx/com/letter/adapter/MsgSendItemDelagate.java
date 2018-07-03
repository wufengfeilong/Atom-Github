package sdwxwx.com.letter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import sdwxwx.com.R;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.letter.activity.LetterChatActivity;
import sdwxwx.com.letter.bean.LetterBean;
import sdwxwx.com.message.activity.FansHomeActivity;


/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<LetterBean>
{
    public Context context;

    public MsgSendItemDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.they_chat_item;
    }

    @Override
    public boolean isForViewType(LetterBean item, int position)
    {
        return !item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder,final LetterBean chatMessage, int position)
    {
        holder.setText(R.id.they_content, chatMessage.getContent());
        Glide.with(context).load(chatMessage.getIcon()).into((ImageView)holder.getView(R.id.they_head_pic));

        holder.setOnClickListener(R.id.they_head_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FansHomeActivity.class);
                intent.putExtra(Constant.INTENT_PARAM,chatMessage.getmMemberId());
                context.startActivity(intent);
            }
        });
    }
}
