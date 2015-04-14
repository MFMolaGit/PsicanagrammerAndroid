package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.engine.ReportGenerator;
import psicanagrammer.gevapps.com.psicanagrammer.engine.SAXReportLoader;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;
import android.widget.*;
import psicanagrammer.gevapps.com.psicanagrammer.utils.*;
import android.content.*;

/**
 * Created by Geva on 06/04/2015.
 */
public class ConsultorReviewAdapter extends BaseAdapter implements ListAdapter {

    private final Activity activity;
	private final Context context;
    private final Vector<String> list;
    private String newReviewName = "";
    private SAXReportLoader xmlLoader;
    private ReportGenerator fileGenerator;

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
        final String selectedItem = list.elementAt(position);

		reviewItem.setText(selectedItem);

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.deleteBtn);
        ImageButton gotoBtn = (ImageButton)view.findViewById(R.id.gotoBtn);
        ImageButton renameBtn = (ImageButton)view.findViewById(R.id.renameBtn);

        final ConsultorReviewAdapter ownReference = this;


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ActivityUtils.showDialogInToast(context.getString(R.string.confirmDeleteTitle, selectedItem),
                        context.getString(R.string.confirmDeleteMessage),
                        context.getResources().getDrawable(android.R.drawable.ic_delete),
                        context.getString(R.string.confirmedDeletedItem, selectedItem), activity, context, list, position, ownReference);
            }
        });

        renameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(context.getString(R.string.confirmRenameItem, selectedItem));
                final String oldXmlFile = Constants.FILE_XML_EXT.replace("*", selectedItem);
                String oldTxtFile = Constants.FILE_TEXT_EXT.replace("*", selectedItem);
                final StringBuilder sFileTxt = new StringBuilder(Constants.FILE_PATH)
                        .append(oldTxtFile);
                final StringBuilder sFileXml = new StringBuilder(Constants.FILE_PATH)
                        .append(oldXmlFile);

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setTextColor(context.getResources().getColor(R.color.black));
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(context.getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newReviewName = input.getText().toString();
                        File fileTxt = new File(sFileTxt.toString());
                        File fileXml = new File(sFileXml.toString());

                        if(newReviewName.isEmpty()) {
                            ActivityUtils.showMessageInToast(context.getString(R.string.renameFail2), context,
                                    context.getResources().getColor(R.color.red), null, false);
                        } else if(!list.contains(newReviewName)) {

                            String txtFile = Constants.FILE_TEXT_EXT.replace("*", newReviewName);
                            StringBuilder sNewFileTxt = new StringBuilder(Constants.FILE_PATH)
                                    .append(txtFile);
                            String xmlFile = Constants.FILE_XML_EXT.replace("*", newReviewName);
                            StringBuilder sNewFileXml = new StringBuilder(Constants.FILE_PATH)
                                    .append(xmlFile);

                            File newFileTxt = new File(sNewFileTxt.toString());
                            File newFileXml = new File(sNewFileXml.toString());
                            boolean renamed = false;

                            if (!newFileXml.exists()) {
                               xmlLoader = new SAXReportLoader(oldXmlFile);
                               Report loaded = xmlLoader.readReport();
                               fileGenerator = new ReportGenerator(loaded, newReviewName);
                               fileGenerator.setResultQuestions(loaded.getResultQuestions());
                               fileGenerator.setTimestamp(loaded.getTimestamp());

                               if((renamed |= fileXml.delete())) {
                                   try {
                                       fileGenerator.generateReport();
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                               }
                            }

                            if (renamed && !newFileTxt.exists()) {
                                renamed |= fileTxt.delete();
                            }

                            if(renamed) {
                                ActivityUtils.showMessageInToast(context.getString(R.string.confirmedRenamedItem, newReviewName),
                                        context, context.getResources().getColor(R.color.white), null, false);

                                list.removeElementAt(position);
                                list.insertElementAt(newReviewName, position);
                                ownReference.notifyDataSetChanged();
                            }
                        } else {
                            ActivityUtils.showMessageInToast(context.getString(R.string.renameFail1), context,
                                    context.getResources().getColor(R.color.yellow), null, false);
                        }
                    }
                });
                builder.setNegativeButton(context.getString(R.string.decline), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        gotoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String fileSelected = list.get(position);

                ActivityUtils.showMessageInToast("Has elegido " + fileSelected, context, context.getResources().getColor(R.color.white), null, false);
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fileName", fileSelected);
                context.startActivity(intent);
            }
        });

        return view;
    }
	
}
