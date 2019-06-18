package com.example.ismael.pikaos.UI.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.RvAvatarsAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserFragment extends Fragment implements UserContract.view{
    public static final String TAG = "UserFragment";

    private UserPresenter presenter;
    private RvAvatarsAdapter adapter;

    private ImageView imgAvatar;
    private ImageButton btChangeAvatar;
    private TextView name;
    private TextInputEditText status;
    private ImageButton btChangePassword;
    private ConstraintLayout layoutPassword;
    private Button btConfirmChangePassword;
    private Button btCancelChangePassword;
    private TextInputEditText actualPassword;
    private TextInputEditText newPassword;
    private TextInputEditText confirmNewPassword;
    private RecyclerView rvAvatars;
    private LinearLayout layoutAvatar;
    private TextView tvChangePassword;

    private View.OnClickListener onClickChangePassword = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(layoutAvatar.getVisibility()==View.VISIBLE)
                layoutAvatar.setVisibility(View.GONE);

            if(layoutPassword.getVisibility()==View.GONE)
                layoutPassword.setVisibility(View.VISIBLE);
            else
                hideChangePasswordLayout();
        }
    };

    private View.OnClickListener onClickCancelChangePassword = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideChangePasswordLayout();
        }
    };

    private View.OnClickListener onClickConfirmChangePassword = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.changePassword(actualPassword.getText().toString(),
                    newPassword.getText().toString(),confirmNewPassword.getText().toString());
        }
    };

    private View.OnFocusChangeListener onFocusChangeStatus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus)
                presenter.changeStatus(status.getText().toString().trim());
        }
    };

    private View.OnClickListener onClickChangeAvatar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(layoutPassword.getVisibility()==View.VISIBLE)
                hideChangePasswordLayout();

            if(layoutAvatar.getVisibility()==View.GONE)
                layoutAvatar.setVisibility(View.VISIBLE);
            else
                layoutAvatar.setVisibility(View.GONE);

            presenter.getAvatars();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user,container,false);
        imgAvatar = rootView.findViewById(R.id.imgAvatarUser);
        btChangeAvatar = rootView.findViewById(R.id.btEditAvatarUser);
        name = rootView.findViewById(R.id.tvNameUser);
        status = rootView.findViewById(R.id.txEtStatusUser);
        btChangePassword = rootView.findViewById(R.id.btEditPasswordProfile);
        tvChangePassword = rootView.findViewById(R.id.tvChangePasswordUser);

        layoutPassword = rootView.findViewById(R.id.layoutPasswordUser);
        btConfirmChangePassword = rootView.findViewById(R.id.btChangePasswordProfile);
        btCancelChangePassword = rootView.findViewById(R.id.btCancelChagePasswordProfile);
        actualPassword = rootView.findViewById(R.id.txtEtActualPasswordUser);
        newPassword = rootView.findViewById(R.id.txtEtNewPassword);
        confirmNewPassword = rootView.findViewById(R.id.txtEtConfirmNewPassword);

        rvAvatars = rootView.findViewById(R.id.rvAvatars);
        layoutAvatar = rootView.findViewById(R.id.layoutAvatarUser);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_user);

        setUserDates();
        presenter = new UserPresenter(this);

        btChangePassword.setOnClickListener(onClickChangePassword);
        tvChangePassword.setOnClickListener(onClickChangePassword);

        btConfirmChangePassword.setOnClickListener(onClickConfirmChangePassword);
        btCancelChangePassword.setOnClickListener(onClickCancelChangePassword);
        status.setOnFocusChangeListener(onFocusChangeStatus);

        btChangeAvatar.setOnClickListener(onClickChangeAvatar);
        imgAvatar.setOnClickListener(onClickChangeAvatar);
    }

    private void setUserDates() {
        if(PikaosApplication.userAvatar!=null)
            Picasso.get().load(PikaosApplication.userAvatar).into(imgAvatar);
        else
            Picasso.get().load(R.mipmap.default_avatar).into(imgAvatar);

        name.setText(PikaosApplication.userName);

        if(PikaosApplication.userStatus!=null)
            status.setText(PikaosApplication.userStatus);
        else
            status.setText("");
    }

    private void hideChangePasswordLayout(){
        layoutPassword.setVisibility(View.GONE);
        actualPassword.setText(null);
        newPassword.setText(null);
        confirmNewPassword.setText(null);
    }

    @Override
    public void onSuccessAvatarChanged(String avatar) {
        layoutAvatar.setVisibility(View.GONE);
        Picasso.get().load(avatar).into(imgAvatar);
    }

    //CONTRACT
    @Override
    public void onSuccessChangedPassword() {
        hideChangePasswordLayout();
        Snackbar.make(getActivity().findViewById(android.R.id.content),"Contraseña cambiada con éxito",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onErrorActualPassword() {
        actualPassword.setError("La contraseña actual es incorrecta");
    }

    @Override
    public void onErrorNewPassword() {
        newPassword.setError(getResources().getString(R.string.error_register_wrong_password));
    }

    @Override
    public void onErrorConfirmPassword() {
        confirmNewPassword.setError(getResources().getString(R.string.error_confirm_password));
        newPassword.setError(getResources().getString(R.string.error_confirm_password));
    }

    @Override
    public void setMessageError(int messageError) {
        Toast.makeText(getActivity(), messageError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucceessChangedStatus() {
        PikaosApplication.userStatus = status.getText().toString();
    }

    @Override
    public void setAvatars(List<String> avatars) {
        adapter = new RvAvatarsAdapter(getContext(),avatars);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String avatar = adapter.getitem(rvAvatars.getChildAdapterPosition(v));
                presenter.changeAvatar(avatar);
            }
        });

        rvAvatars.setAdapter(adapter);
        rvAvatars.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
}
