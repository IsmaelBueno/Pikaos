package com.example.ismael.pikaos.UI.user;

import java.util.List;

public interface UserContract {

    interface presenter{
        void changePassword(String actualPassword,String newPassword, String confirmNewPassword);
        void changeStatus(String status);
        void getAvatars();
        void changeAvatar(String avatar);
    }

    interface view{
        void onSuccessAvatarChanged(String avatar);
        void onSuccessChangedPassword();
        void onErrorActualPassword();
        void onErrorNewPassword();
        void onErrorConfirmPassword();
        void setMessageError(int messageError);
        void onSucceessChangedStatus();
        void setAvatars(List<String> avatars);
    }
}
