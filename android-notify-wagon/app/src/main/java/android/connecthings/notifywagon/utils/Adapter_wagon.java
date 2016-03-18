package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.model.Wagon;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ssr on 18/03/16.
 */
public class Adapter_wagon extends PagerAdapter {
    List<Wagon> wagons;


    @Override
    public int getCount() {
        return wagons.size();
    }

    @Override
    public boolean isViewFromObject(View collection, Object object) {
        return collection == ((View) object);
    }

    public Adapter_wagon(List<Wagon> wagonList) {
        super();
        this.wagons = wagonList;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        // Inflating layout
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.layout_friends, null);
       // call textview

        TextView voiture_number = (TextView) view.findViewById(R.id.tv_voiture_information);
        TextView user_txt = (TextView) view.findViewById(R.id.tv_user_name);
        TextView secondUser_txt = (TextView) view.findViewById(R.id.tv_second_user_name);
        TextView userNumber = (TextView) view.findViewById(R.id.tv_numberOfUser);
        try {
            Wagon wagonObject = wagons.get(position);
            List<String> friendWagon = wagonObject.getUsers();
            String user = friendWagon.get(position);
            user_txt.setText(user);
            voiture_number.setText("Voiture: "+wagonObject.getPosition()+1 +"");
            if (friendWagon.get(position +1) != null){
                String second = friendWagon.get(position +1 );
                secondUser_txt.setText(second);
            }
            if (getCount()>2 && position <2){
                int count = getCount()-2 ;
                userNumber.setText("et %d autres personnes"+count);
            }
            // update view
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
