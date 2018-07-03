package sdwxwx.com.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by 860115039
 * date      2018/5/8
 * time      14:57
 */
public class TitleCompatLayout extends AppBarLayout {
    public TitleCompatLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleCompatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        View statusBar = new View(context);
        statusBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        addView(statusBar);

    }
}
