package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.model.Wagon;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AdapterWagon extends PagerAdapter {
    List<Wagon> wagons;
    NwBeacon nwBeacon;
    @Override
    public int getCount() {

        if (wagons.size()>0){
            return wagons.size();
        }
        return  0;
    }

    @Override
    public boolean isViewFromObject(View collection, Object object) {
        return collection == ((View) object);
    }

    public AdapterWagon(){
        this.wagons = new ArrayList<Wagon>(); ;
    }

    public AdapterWagon(List<Wagon> wagonList) {
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
        TextView tvVoitureNumber = (TextView) view.findViewById(R.id.tv_voiture_information);
        TextView tvUser = (TextView) view.findViewById(R.id.tv_user_name);
        TextView tvUserSecond = (TextView) view.findViewById(R.id.tv_second_user_name);
        TextView tvUserNumber = (TextView) view.findViewById(R.id.tv_numberOfUser);

        try {
            Wagon wagonObject = wagons.get(position);
            List<String> friendWagon = wagonObject.getUsers();
            String user = friendWagon.get(position);
            int nextFriend =  position + 1 ;
            if (user.length()>0){
                 tvUser.setVisibility(View.VISIBLE);
                 tvUser.setText(user);
            }
            //if (wagonObject.getPosition()+1>0){
            String placeName = nwBeacon.getValue(AdtagModel.CATEGORY.PLACE,AdtagModel.FIELD.NAME);
               tvVoitureNumber.setText(placeName);
           // }
            if (friendWagon.get(nextFriend) != null){
                tvUserSecond.setVisibility(View.VISIBLE);
                String second = friendWagon.get(position +1);
                tvUserSecond.setText(second);
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    public  void updateListe (NwBeacon currentBeacon) {
        this.wagons.clear();
        nwBeacon = currentBeacon;
        this.wagons.addAll(currentBeacon.getBox().getWagonBox().getWagons());
        this.notifyDataSetChanged();
    }

}
