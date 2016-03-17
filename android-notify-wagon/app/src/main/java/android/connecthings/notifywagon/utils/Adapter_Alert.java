package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ssr on 17/03/16.
 */
public  class Adapter_Alert extends PagerAdapter {
    String[] desc;
    int[] image;

    @Override
    public boolean isViewFromObject(View collection, Object object) {

        return collection == ((View) object);
    }
    public Adapter_Alert(String[] desc, int[] image) {

        super();
        this.desc = desc;
        this.image = image;

    }

    @Override
    public int getCount() {
        return desc.length;
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        // Inflating layout
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.layout_alert_message, null);
        TextView itemText = (TextView) view.findViewById(R.id.textViewMain);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewmain);

        try {
            itemText.setText(desc[position]);
            imageView.setImageResource(image[position]);
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
