package lynx.coc.multitool;

import android.view.View;

/**
 * Self Explanatory
 */

public interface PushDown{

    PushDown i( View.OnClickListener clickListener );

    PushDown h( View.OnTouchListener eventListener );
}

