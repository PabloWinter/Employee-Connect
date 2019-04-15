package ca.bvc.employeeconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ca.bvc.employeeconnect.R;
import ca.bvc.employeeconnect.model.RequestDayOff;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestListHolder>{

    private Context context;
    private List<RequestDayOff> listDaysOff;

    public RequestListAdapter(Context context, List<RequestDayOff> daysOff) {
        this.context = context;
        this.listDaysOff = daysOff;
    }

    @NonNull
    @Override
    public RequestListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_items, parent, false);
        RequestListHolder holder = new RequestListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListHolder dayListHolder, final int position) {

        dayListHolder.nameUser.setText(listDaysOff.get(position).nameUser);
        dayListHolder.employeeCode.setText(listDaysOff.get(position).employeeCode);
        dayListHolder.reasonToDayOff.setText(listDaysOff.get(position).reasonDayOff);
        dayListHolder.dateDayOff.setText(listDaysOff.get(position).dateDayOff);
    }

    @Override
    public int getItemCount() {
        return listDaysOff.size();
    }

    public class RequestListHolder extends RecyclerView.ViewHolder {
        TextView nameUser;
        TextView employeeCode;
        TextView reasonToDayOff;
        TextView dateDayOff;

        public RequestListHolder(@NonNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.request_employee_name);
            employeeCode = itemView.findViewById(R.id.request_employee_code);
            reasonToDayOff = itemView.findViewById(R.id.request_employee_reason);
            dateDayOff = itemView.findViewById(R.id.request_employee_date);
        }
    }

}
