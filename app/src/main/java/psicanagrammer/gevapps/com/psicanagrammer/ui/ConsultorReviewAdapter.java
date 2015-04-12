package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;
import android.widget.*;
import psicanagrammer.gevapps.com.psicanagrammer.utils.*;
import android.content.*;

/**
 * Created by Geva on 06/04/2015.
 */
public class ConsultorReviewAdapter extends BaseAdapter {

    private final Activity activity;
	private final Context context;
    private final Vector<String> list;

    public ConsultorReviewAdapter(final Activity activity, Vector<String> list, final Context context) {
        super();
        this.activity = activity;
		this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.elementAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.consultor_review_element,null,true);
        TextView reviewItem = (TextView) view.findViewById(R.id.reviewLabel);
		reviewItem.setText(list.elementAt(position));

        return view;
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String fileSelected = list.get(position);

		ActivityUtils.showMessageInToast("Has elegido " + fileSelected, view.getContext(), view.getResources().getColor(R.color.white), null, false);
		Intent intent = new Intent(view.getContext(), ReviewActivity.class);
		intent.putExtra("fileName", fileSelected);
		context.startActivity(intent);
	}

	public void deleteReview(final View view) {
		ActivityUtils.showMessageInToast("Has borrado " + "##", context, context.getResources().getColor(R.color.white), null, false);
	}
	
}
