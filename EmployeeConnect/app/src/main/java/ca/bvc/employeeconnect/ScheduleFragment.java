package ca.bvc.employeeconnect;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.util.Date;

import ca.bvc.employeeconnect.helper.MyDate;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.EventViewModel;
import ca.bvc.employeeconnect.viewmodel.UserViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ScheduleFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private CalendarView myCalender;

    private OnFragmentInteractionListener mListener;

    private EventViewModel eventViewModel;
    private FloatingActionButton floatingActionButton;
    private Date selectedDate = new Date();

    public static String EXTRA_SELECTED_DATE = "SELECTED_DATE";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        final EventViewModel eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        final RecyclerView recyclerView = rootView.findViewById(R.id.event_recycler_view);
        myCalender = (CalendarView)rootView.findViewById(R.id.calendarView);
        myCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Timestamp selectedTimestamp = MyDate.getDate(dayOfMonth, month, year);
                selectedDate = selectedTimestamp.toDate();
                eventViewModel.initEventRecycler(getActivity(), recyclerView, selectedTimestamp);
            }
        });



        eventViewModel.initEventRecycler(getActivity(), recyclerView, MyDate.getCurrentTimeStamp());

        floatingActionButton = rootView.findViewById(R.id.floating_button);

        //set on click listener for opening schedule assign page or request day off page based on user isAdmin Status
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
                User user = userViewModel.getUser((getActivity()));

                if (user.isAdmin()) {
                    Intent intent = new Intent(getContext(), UserScheduleForm.class);
                    intent.putExtra(EXTRA_SELECTED_DATE, selectedDate.getTime());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), RequestDayOffActivity.class);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
