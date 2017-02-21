package hg.crx.findpoint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hg.crx.findpoint.R;

/**
 * Created by Sawatari on 2016/11/18.
 */

public class AboutDialog extends Dialog {
    public AboutDialog(Context context) {
        super(context);
    }

    public AboutDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        public Builder (Context context){
            this.context = context;
        }
        public AboutDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final AboutDialog dialog = new AboutDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.about_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button posButton = (Button)layout.findViewById(R.id.positiveButton);
            posButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }
}
