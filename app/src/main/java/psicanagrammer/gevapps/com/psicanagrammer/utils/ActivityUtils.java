package psicanagrammer.gevapps.com.psicanagrammer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;

import java.io.File;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.ui.ConsultorReviewAdapter;

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

    public static void showDialogInToast(final String title, final String message, final Drawable icon,
                                         final String text, final Activity activity, final Context context,
                                         final Vector<String> list, final int item, final ConsultorReviewAdapter ownReference) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(icon);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                StringBuilder sFileTxt = new StringBuilder(Constants.FILE_PATH)
                        .append(Constants.FILE_TEXT_EXT.replace("*",list.get(item)));
                StringBuilder sFileXml = new StringBuilder(Constants.FILE_PATH)
                        .append(Constants.FILE_XML_EXT.replace("*",list.get(item)));

                File fileTxt = new File(sFileTxt.toString());
                File fileXml = new File(sFileXml.toString());
                boolean erased = false;

                    if(fileTxt.exists()) {
                       erased = fileTxt.delete();
                    }

                    if(fileXml.exists()) {
                       erased |= fileXml.delete();
                    }
                    if(erased) {
                        list.remove(item);
                    }

                ownReference.notifyDataSetChanged();

                ActivityUtils.showMessageInToast(text, context, context.getResources().getColor(R.color.white), null, false);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.create();

        alertDialog.show();
    }

	public static void vibrate(final Context context, final long milliseconds) {
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(milliseconds);
	}
	
}
