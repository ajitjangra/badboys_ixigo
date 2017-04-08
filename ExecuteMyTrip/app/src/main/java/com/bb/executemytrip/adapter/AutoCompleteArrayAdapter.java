package com.bb.executemytrip.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bb.executemytrip.R;
import com.bb.executemytrip.model.AutoCompleteCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajitjangra on 4/8/17.
 */

public class AutoCompleteArrayAdapter extends ArrayAdapter<AutoCompleteCityModel> {
  private final ArrayList<AutoCompleteCityModel> alAutoCompleteModelSuggestion;
  private ArrayList<AutoCompleteCityModel> alAutoCompleteModel;
  private final ArrayList<AutoCompleteCityModel> alAutoCompleteModelAll;

  private Context mContext;
  private final int mLayoutResourceId;

  public AutoCompleteArrayAdapter(final Context context, final int resource, final ArrayList<AutoCompleteCityModel> alAutoCompleteModel) {
    super(context, resource);
    this.mContext = context;
    this.mLayoutResourceId = resource;
    this.alAutoCompleteModel = alAutoCompleteModel;
    this.alAutoCompleteModel = new ArrayList<>(alAutoCompleteModel);
    this.alAutoCompleteModelAll = new ArrayList<>(alAutoCompleteModel);
    this.alAutoCompleteModelSuggestion = new ArrayList<>();
  }

  public int getCount() {
    return alAutoCompleteModel.size();
  }

  public AutoCompleteCityModel getItem(int position) {
    return alAutoCompleteModel.get(position);
  }

  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    try {
      if (convertView == null) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(mLayoutResourceId, parent, false);
      }
      AutoCompleteCityModel autoCompleteCityModel = getItem(position);
      TextView name = (TextView) convertView.findViewById(R.id.tv_code);
      name.setText(autoCompleteCityModel.text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return convertView;
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      public String convertResultToString(Object resultValue) {
        return ((AutoCompleteCityModel) resultValue).text;
      }

      @Override
      protected Filter.FilterResults performFiltering(CharSequence constraint) {
        if (constraint != null) {
          alAutoCompleteModelSuggestion.clear();
          for (AutoCompleteCityModel department : alAutoCompleteModelAll) {
            if (department.text.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
              alAutoCompleteModelSuggestion.add(department);
            }
          }
          FilterResults filterResults = new FilterResults();
          filterResults.values = alAutoCompleteModelSuggestion;
          filterResults.count = alAutoCompleteModelSuggestion.size();
          return filterResults;
        } else {
          return new FilterResults();
        }
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        alAutoCompleteModel.clear();
        if (results != null && results.count > 0) {
          // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
          List<?> result = (List<?>) results.values;
          for (Object object : result) {
            if (object instanceof AutoCompleteCityModel) {
              alAutoCompleteModel.add((AutoCompleteCityModel) object);
            }
          }
        } else if (constraint == null) {
          // no filter, add entire original list back in
          alAutoCompleteModel.addAll(alAutoCompleteModelAll);
        }
        notifyDataSetChanged();
      }
    };
  }

}
