package com.dejun.androidcourse.activity;

/**
 * startActivityForResult的requestCode及setResult的resultCode
 * Created by TwistDing on 2018/8/21.
 */
public interface ActivityCode {
    /**
     * 程序运行时所必需的运行时权限申请
     */
    int REQUEST_ESSENTIAL_PERMISSIONS = 0xf001;
    /**
     * 拍照
     */
    int REQUEST_TAKE_PHOTO = 0xf002;
    /**
     * 相册选择
     */
    int REQUEST_ALBUM = 0xf003;
    /**
     * 裁剪
     */
    int REQUEST_PHOTO_ZOOM = 0xf004;
    /**
     * 调用系统发送短信
     */
    int REQUEST_SEND_SMS = 0xf005;
    /**
     * 调用系统拨打电话
     */
    int REQUEST_CALL = 0xf006;

    /**
     * 更换手机号
     */
    int REQUEST_MODIFY_PHONE_NUMBER = 0xa05;

    /**
     * 修改个人昵称
     */
    int REQUEST_CHANGE_NICKNAME = 0xa06;
    int RESULT_CHANGE_NICKNAME = 0xb06;

    /**
     * 修改个性签名
     */
    int REQUEST_CHANGE_SINATURE = 0xa07;
    int RESULT_CHANGE_SINATURE = 0xb07;
    /**
     * 修改姓名
     */
    int REQUEST_CHANGE_NAME = 0xa08;
    int RESULT_CHANGE_NAME = 0xb08;

    /**
     * 修改电话
     */
    int REQUEST_CHANGE_PHONE = 0xa09;
    int RESULT_CHANGE_PHONE = 0xb09;
    /**
     * 应用安装时未知应用设置界面打开
     */
    int REQUEST_GET_UNKNOWN_APP_SOURCE = 0xa09;

    /**
     * 创建手势密码请求码
     */
    int REQUEST_CREATE_GESTURE_LOCK = 0xa0a;
    /**
     * 关闭手势密码请求码
     */
    int REQUEST_CLOSE_GESTURE_LOCK = 0xa0b;
    /**
     * 开启问题验证请求码
     */
    int REQUEST_OPEN_VERIFY_QUESTION = 0xa0c;

    /**
     * 进入注册页
     */
    int REQUEST_REGISTER = 0xa0d;

    /********** 页面跳转的请求码与结果码 **********/
    /**
     * 进入通讯录邀请页
     */
    int REQUEST_START_INVITE_CONTACTS = 0xa10;

    /**
     * 进入用户详情页
     */
    int REQUEST_START_PERSONAL_INFO = 0xa11;
    /**
     * 返回用户信息
     * value为int
     */
    int RESULT_PERSONAL_INFO_USER_INFO = 0xb11;

    /**
     * 进入设置用户备注页
     */
    int REQUEST_START_SET_USER_NOTES = 0xa12;

    /**
     * 进入群资料页
     */
    int REQUEST_START_TEAM_INFORMATION = 0xa13;
    /**
     * 返回群名称
     */
    int RESULT_TEAM_NAME = 0xb14;
    /**
     * 退群返回码
     */
    int RESULT_LEAVE_TEAM = 0xb141;
    /**
     * 解散群返回码
     */
    int RESULT_DISBAND_TEAM = 0xb142;

    /**
     * 进入群资料编辑页
     */
    int REQUEST_START_TEAM_INFO_EDIT = 0xa14;

    /**
     * 进入群成员查看/踢人页
     */
    int REQUEST_START_TEAM_MEMBERS = 0xa15;

    /**
     * 进入群管理员管理页
     */
    int REQUEST_START_TEAM_MANAGER_SETTING = 0xa16;

    /**
     * 进入添加群管理员页
     */
    int REQUEST_START_TEAM_ADD_MANAGER = 0xa17;

    /**
     * 进入群转让页
     */
    int REQUEST_START_TEAM_TRANSFER = 0xa18;

    /**
     * 进入拉好友进群页
     */
    int REQUEST_START_TEAM_INVITE_FRIEND = 0xa19;

    /**
     * 进入个人相册
     */
    int REQUEST_START_PERSONAL_ALBUM = 0xa1a;
    /**
     * 进入工作相册
     */

    /**
     * 进入上传个人相册页
     */
    int REQUEST_START_UPLOAD_PERSONAL_ALBUM = 0xa1b;

    /**
     * 进入上传相册的预览页
     */
    int REQUEST_START_UPLOAD_ALBUM_PREVIEW = 0xa1c;

    /**
     * 进入群相册页
     */
    int REQUEST_START_TEAM_ALBUM = 0xa1d;

    /**
     * 进入上传群相册页
     */
    int REQUEST_START_UPLOAD_TEAM_ALBUM = 0xa1e;

    /**
     * 进入信任积分页
     */
    int REQUEST_START_INTEGRAL = 0xa1f;
    /**
     * 进入区域选择界面
     */
    int REQUEST_LOCATION = 0xa2a;
    int RESULT_LOCATION = 0xa2b;
    int REQUEST_START_WORK_ALBUM = 0xa2c;
    /**
     * 进入工作相册
     */
    /**
     * 进入工作资料
     */
    int REQUEST_START_WORK_INFO = 0xa2d;
    int RESULT_START_WORK_INFO = 0xa2d;
    int REQUEST_EDIT_WORK_INFO = 0xa2e;
    /**
     * 名片
     */
    int REQUEST_OTHER_CARD = 0xa2f;
    int RESULT_OTHER_CARD = 0xa31;

    /**
     * 邀请名额转让
     */
    int REQUEST_INVITE_TRANSFER = 0xa30;
    /**
     * 聊天备注
     */
    int REQUEST_CHAT_REMARK = 0xa32;
    int RESULT_CHAT_REMARK = 0xa33;
    /**
     * 派信钱包密码设置
     */
    int REQUEST_PASSIONET_PDW=0xa34;
    /**
     * 前去红包详情
     */
    int REQUEST_RED_PACKET_DETAIL=0xa35;
    /**
     * 派圈进入话题页的请求码
     */
    int REQUEST_CIRCLE_CONVERSATION=0xa36;
    /**
     * 派圈进入话题页结果码
     */
    int RESULT_CIRCLE_CONVERSATION=0xa37;
}
