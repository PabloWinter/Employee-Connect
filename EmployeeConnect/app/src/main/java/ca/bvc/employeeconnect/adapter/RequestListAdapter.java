package ca.bvc.employeeconnect.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.bvc.employeeconnect.R;
import ca.bvc.employeeconnect.model.RequestDayOff;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.UserViewModel;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestListHolder>{

    private Context mcontext;
    private List<RequestDayOff> listDaysOff;

    /**
     * Constructor for RequestListAdapter
     * @param context
     * @param daysOff
     */
    public RequestListAdapter(Context context, List<RequestDayOff> daysOff) {
        this.mcontext = context;
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

        /**
         * Constructor for Request List Holder
         * @param itemView
         */
        public RequestListHolder(@NonNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.request_employee_name);
            employeeCode = itemView.findViewById(R.id.request_employee_code);
            reasonToDayOff = itemView.findViewById(R.id.request_employee_reason);
            dateDayOff = itemView.findViewById(R.id.request_employee_date);
        }
    }
}
