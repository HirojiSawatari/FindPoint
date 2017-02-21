package hg.crx.findpoint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hg.crx.findpoint.R;

/**
 * Created by Sawatari on 2016/11/22.
 */

public class TrackDialog extends Dialog {


    public interface OnTrackSetListener{
        public void posButtonClick(String startPoint, String endPoint);
    }

    public TrackDialog(Context context) {
        super(context);
    }

    public TrackDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private static OnTrackSetListener listener;
        private Context context;
        String startPoint;
        String endPoint;

        public Builder (Context context){
            this.context = context;
        }

        public TrackDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final TrackDialog dialog = new TrackDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.track_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button posButton = (Button)layout.findViewById(R.id.positiveButton);
            Button negButton = (Button)layout.findViewById(R.id.negativeButton);
            final EditText startEdit = (EditText)layout.findViewById(R.id.startEdit);
            final EditText endEdit = (EditText)layout.findViewById(R.id.endEdit);
            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(startEdit.getText().toString().length() > 0 && endEdit.getText().toString().length() > 0) {
                        startPoint = startEdit.getText().toString();
                        endPoint = endEdit.getText().toString();
                        listener.posButtonClick(startPoint, endPoint);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context.getApplicationContext(), "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            negButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }

        public void setOnButtonClickListener(OnTrackSetListener listener) {
            this.listener = listener;
        }

    }

}