package sdwxwx.com.letter.adapter;

import android.content.Context;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import sdwxwx.com.letter.bean.LetterBean;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<LetterBean>
{
    public ChatAdapterForRv(Context context, List<LetterBean> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate(context));
        addItemViewDelegate(new MsgComingItemDelagate(context));
    }
}
