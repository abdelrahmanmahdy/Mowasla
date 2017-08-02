package com.overcoffee.internshipapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesham on 11/07/2017.
 */

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
    private ArrayList<ResultItem> all_result_items;
    private Context context;

    public ResultsAdapter(List<ResultItem> all_result_items, Context context)
        {
        this.all_result_items = new ArrayList<>();

        //copying into a new list
        for (ResultItem ri : all_result_items)
            this.all_result_items.add(ri);

        this.context = context;
        }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
        switch (viewType)
            {
            case 0:
                return new ResultItemVH(
                        LayoutInflater.from(context).inflate(R.layout.result_frame_small, parent, false)
                );
            //case 1:
            }
        return null; // WILL PROBABLY CRASH!
        }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
        {
        switch (holder.getItemViewType())
            {
            case 0:
                ResultItemVH VH = (ResultItemVH) holder;
                VH.ET_directions.setText((all_result_items.get(position)).getDirection());
                VH.ET_estimated_price.setText((all_result_items.get(position)).getEstPrice());
                VH.ET_estimated_time.setText((all_result_items.get(position)).getEstTime());
              //  VH.ET_description.setText((all_result_items.get(position)).getDescription());
                break;
            }
        }

    @Override
    public int getItemCount()
        {
        return all_result_items.size();
        }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position)
        {
        if (all_result_items.get(position) instanceof ResultItem)
            return 0;
        //} else if (all_result_items.get(position) instanceof SpinnerFieldObj)
        // {
        // return 1;
        //  }
        return -1; // WILL PROBABLY CRASH!
        }

    class ResultItemVH extends RecyclerView.ViewHolder
        {
        private TextView ET_directions;
        private TextView ET_estimated_price;
        private TextView ET_estimated_time;
      //  private TextView ET_description;

        public ResultItemVH(View itemView)
            {
            super(itemView);

            ET_directions = (TextView) itemView.findViewById(R.id.small_text_directions);
            ET_estimated_price = (TextView) itemView.findViewById(R.id.small_text_estimated_price);
            ET_estimated_time = (TextView) itemView.findViewById(R.id.small_text_estimated_time);
          //  ET_description = (TextView) itemView.findViewById(R.id.text_description);
            }
        }
    }

class ResultItem
    {
    private String direction;
    private String estTime;
    private String estPrice;
  //  private String description;


    public ResultItem(String direction, String estTime, String estPrice, String description)
        {
        this.direction = direction;
        this.estTime = estTime;
        this.estPrice = estPrice;
      //  this.description = description;
        }

    public String getDirection()
        {
        return direction;
        }

    public String getEstTime()
        {
        return estTime;
        }

    public String getEstPrice()
        {
        return estPrice;
        }

  /* public String getDescription()
        {
        return description;
        }*/
    }