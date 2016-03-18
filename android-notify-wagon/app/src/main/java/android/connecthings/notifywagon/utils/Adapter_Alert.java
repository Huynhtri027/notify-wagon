package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.Message;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ssr on 17/03/16.
 */
public  class Adapter_Alert extends PagerAdapter {
    List<Message> messages ;
    @Override
    public boolean isViewFromObject(View collection, Object object) {

        return collection == ((View) object);
    }
    public Adapter_Alert(List<Message> messageList) {
        super();
        this.messages = messageList;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        // Inflating layout
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.layout_alert_message, null);
        TextView message_txt = (TextView) view.findViewById(R.id.tv_textMessageAlert);
        TextView type_txt = (TextView) view.findViewById(R.id.tv_typeAlert);
        TextView sender_txt = (TextView) view.findViewById(R.id.tv_sender_message_alert);

        try {
            Message messageObject = messages.get(position);
            String messageText = messageObject.getMessage() ;
            message_txt.setText(messageText+"");
            type_txt.setText(messageObject.getType().toString()+"");
            sender_txt.setText(messageObject.getSender()+"");
           // imageView.setImageResource(image[position]);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);

    }

}
