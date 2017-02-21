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
 * Created by Sawatari on 2016/11/18.
 */

public class LatlngDialog extends Dialog {


    public interface OnLatlngSetListener{
        public void posButtonClick(double pointLat, double pointLng);
    }

    public LatlngDialog(Context context) {
        super(context);
    }

    public LatlngDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private static OnLatlngSetListener listener;
        private Context context;
        double pointLat = 0.0;
        double pointLng = 0.0;

        public Builder (Context context){
            this.context = context;
        }

        public LatlngDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LatlngDialog dialog = new LatlngDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.latlng_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button posButton = (Button)layout.findViewById(R.id.positiveButton);
            Button negButton = (Button)layout.findViewById(R.id.negativeButton);
            final EditText latEdit = (EditText)layout.findViewById(R.id.latEdit);
            final EditText lngEdit = (EditText)layout.findViewById(R.id.lngEdit);
            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(latEdit.getText().toString().length() > 0 && lngEdit.getText().toString().length() > 0) {
                        pointLat = Double.parseDouble(latEdit.getText().toString());
                        pointLng = Double.parseDouble(lngEdit.getText().toString());
                        if ((pointLat >= -90 && pointLat <= 90) && (pointLng >= -180 && pointLng <= 180)) {
                            listener.posButtonClick(pointLat, pointLng);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "经纬度输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context.getApplicationContext(), "经纬度输入有误，请重新输入", Toast.LENGTH_SHORT).show();
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

        public void setOnButtonClickListener(OnLatlngSetListener listener) {
            this.listener = listener;
        }

    }

}
