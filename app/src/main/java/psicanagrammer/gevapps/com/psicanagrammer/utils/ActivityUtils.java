package psicanagrammer.gevapps.com.psicanagrammer.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Geva on 20/02/2015.
 */
public class ActivityUtils {

    private static final int DEFAULT_FONT_SIZE = 25;
    private static final int DECREMENTAL_FONT_SIZE = 5;

    public static int getIdentifier(final Activity activity, final String resourceType, final String resourceName) {
        return activity.getResources().getIdentifier(resourceName, resourceType, activity.getPackageName());
    }

    public static void showMessageInToast(final String message, final Context context, final int color, final Integer size, final boolean isHtml) {
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);

        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);

            if(isHtml) {
                messageTextView.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE);
            } else {
                messageTextView.setTextColor(color);
            }

        int finalSize = DEFAULT_FONT_SIZE;
            if(size != null) {
                finalSize = size;
            }

            messageTextView.setTextSize(finalSize);

        toast.show();
    }

}
