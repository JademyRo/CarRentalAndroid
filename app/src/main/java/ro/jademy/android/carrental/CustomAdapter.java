package ro.jademy.android.carrental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ro.jademy.android.carrental.viewmodel.CarItem;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<CarItem> implements View.OnClickListener {

    private Context mContext;

    private static class ViewHolder {
        private ImageView carImage;
        private TextView carMakeTextView;
        private TextView carModelTextView;
    }

    public CustomAdapter(Context context, List<CarItem> data) {
        super(context, R.layout.car_list_item, data);
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

//        int position = (Integer) v.getTag();
//        Object object = getItem(position);
//        CarItem CarItem = (CarItem) object;
//
//        switch (v.getId()) {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " + CarItem.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }

        Toast.makeText(mContext, "Clicked!", Toast.LENGTH_SHORT).show();
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CarItem carItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.car_list_item, parent, false);
            viewHolder.carMakeTextView = (TextView) convertView.findViewById(R.id.carMakeTextView);
            viewHolder.carModelTextView = (TextView) convertView.findViewById(R.id.carModelTextView);
            viewHolder.carImage = (ImageView) convertView.findViewById(R.id.carImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        viewHolder.carMakeTextView.setText(carItem.getMake());
        viewHolder.carModelTextView.setText(carItem.getModel());
        viewHolder.carImage.setImageDrawable(mContext.getResources().getDrawable(carItem.getImageResourceId(), null));

        // viewHolder.carMakeTextView.setOnClickListener(this);

        // Return the completed view to render on screen
        return convertView;
    }
}
