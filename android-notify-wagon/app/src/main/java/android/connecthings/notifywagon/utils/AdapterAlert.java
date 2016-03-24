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

import java.util.ArrayList;
import java.util.List;

/**
 */
public  class AdapterAlert extends PagerAdapter {
    List<Message> messages ;
    @Override
    public boolean isViewFromObject(View collection, Object object) {

        return collection == ((View) object);
    }
    public AdapterAlert(List<Message> messageList) {
        super();
        this.messages = messageList;
    }

    public AdapterAlert() {
        this.messages = new ArrayList<Message>(); ;
    }

    @Override
    public int getCount() {
        if (messages.size()>0){
            return messages.size();
        }
        return  0;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        // Inflating layout
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.layout_alert_message, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_textMessageAlert);
        TextView tvTypeAlert = (TextView) view.findViewById(R.id.tv_typeAlert);
        TextView tvSender = (TextView) view.findViewById(R.id.tv_sender_message_alert);

        try {
            Message messageObject = messages.get(position);
            String messageText = messageObject.getMessage() ;
            tvMessage.setText(messageText+"");
            tvTypeAlert.setText(messageObject.getType().getTitleId());
            tvSender.setText(messageObject.getSender()+"");
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    public  void updateListe (List<Message> messageList) {
        this.messages.clear();
        if(messageList==null){
            messageList = new ArrayList<Message>();
        }
        this.messages.addAll(messageList);
        notifyDataSetChanged();
    }

}
