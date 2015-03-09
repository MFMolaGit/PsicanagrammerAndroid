package psicanagrammer.gevapps.com.psicanagrammer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Geva on 20/02/2015.
 */
public class ActivityUtils {
    public static int getIdentifier(final Activity activity, final String resourceType, final String resourceName) {
        return activity.getResources().getIdentifier(resourceName, resourceType, activity.getPackageName());
    }

    public static void showMessageInToast(final String message, final Context context, final int color) {
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);

        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(25);
        messageTextView.setTextColor(color);

        toast.show();
    }

}
