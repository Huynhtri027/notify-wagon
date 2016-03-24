package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.Message;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AdapterFriends extends PagerAdapter {
    List<Message> messages ;
    @Override
    public boolean isViewFromObject(View collection, Object object) {

        return collection == ((View) object);
    }

    public AdapterFriends() {
    this.messages = new ArrayList<Message>(); ;

    }

    public AdapterFriends(List<Message> messageList) {
        super();
        this.messages = messageList;
    }

    @Override
    public int getCount() {
        if (messages.size()>0 ){
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
        View view = inflater.inflate(R.layout.layout_message_friends, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_friendsMessage);
        TextView tvSender = (TextView) view.findViewById(R.id.tv_friendSender);

        try {
            Message messageObject = messages.get(position);
            String messageText = messageObject.getMessage() ;
            tvMessage.setText(messageText + "");
            tvSender.setText(messageObject.getSender() + "");
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
    public  void updateListe (List<Message> messageList) {
        this.messages = messageList;
    }
}
