package pk.com.ke.complaint.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class DisplayDialog implements Runnable {
        private final Context mContext;
        String mText;

        public DisplayDialog(Context mContext, String text){
            this.mContext = mContext;
            mText = text;
        }

        public void run(){
            new AlertDialog.Builder(mContext)
                    .setMessage(mText)
                    .setPositiveButton("OK", null).show();
        }

}
