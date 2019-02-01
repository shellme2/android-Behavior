/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/hesn/git/android/M1000/new_bfc/MobileBehavior/BfcBehaviorAidlSdk/src/main/aidl/com/eebbk/bfc/sdk/behavior/aidl/IBehaviorAidlInterface.aidl
 */
package com.eebbk.bfc.sdk.behavior.aidl;
// Declare any non-default types here with import statements

public interface IBehaviorAidlInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements IBehaviorAidlInterface
{
private static final String DESCRIPTOR = "com.eebbk.bfc.sdk.behavior.aidl.IBehaviorAidlInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.eebbk.bfc.sdk.behavior.aidl.IBehaviorAidlInterface interface,
 * generating a proxy if needed.
 */
public static IBehaviorAidlInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof IBehaviorAidlInterface))) {
return ((IBehaviorAidlInterface)iin);
}
return new Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_event:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
String _arg3;
_arg3 = data.readString();
String _arg4;
_arg4 = data.readString();
String _arg5;
_arg5 = data.readString();
String _arg6;
_arg6 = data.readString();
String _arg7;
_arg7 = data.readString();
String _arg8;
_arg8 = data.readString();
String _arg9;
_arg9 = data.readString();
String _arg10;
_arg10 = data.readString();
String _arg11;
_arg11 = data.readString();
String _arg12;
_arg12 = data.readString();
String _arg13;
_arg13 = data.readString();
String _arg14;
_arg14 = data.readString();
this.event(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11, _arg12, _arg13, _arg14);
reply.writeNoException();
return true;
}
case TRANSACTION_eventJson:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
String _arg1;
_arg1 = data.readString();
String _result = this.eventJson(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_realTime2Upload:
{
data.enforceInterface(DESCRIPTOR);
this.realTime2Upload();
reply.writeNoException();
return true;
}
case TRANSACTION_getBehaviorVersion:
{
data.enforceInterface(DESCRIPTOR);
String _result = this.getBehaviorVersion();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_basicTypes:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
long _arg1;
_arg1 = data.readLong();
boolean _arg2;
_arg2 = (0!=data.readInt());
float _arg3;
_arg3 = data.readFloat();
double _arg4;
_arg4 = data.readDouble();
String _arg5;
_arg5 = data.readString();
this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements IBehaviorAidlInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void event(int type, String curActivityName, String functionName, String moduleDetail, String trigValue, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend, String innerExtend) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeString(curActivityName);
_data.writeString(functionName);
_data.writeString(moduleDetail);
_data.writeString(trigValue);
_data.writeString(extend);
_data.writeString(dataId);
_data.writeString(dataTitle);
_data.writeString(dataEdition);
_data.writeString(dataType);
_data.writeString(dataGrade);
_data.writeString(dataSubject);
_data.writeString(dataPublisher);
_data.writeString(dataExtend);
_data.writeString(innerExtend);
mRemote.transact(Stub.TRANSACTION_event, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public String eventJson(int type, String json) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeString(json);
mRemote.transact(Stub.TRANSACTION_eventJson, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void realTime2Upload() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_realTime2Upload, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public String getBehaviorVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBehaviorVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(anInt);
_data.writeLong(aLong);
_data.writeInt(((aBoolean)?(1):(0)));
_data.writeFloat(aFloat);
_data.writeDouble(aDouble);
_data.writeString(aString);
mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_event = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_eventJson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_realTime2Upload = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getBehaviorVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void event(int type, String curActivityName, String functionName, String moduleDetail, String trigValue, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend, String innerExtend) throws android.os.RemoteException;
public String eventJson(int type, String json) throws android.os.RemoteException;
public void realTime2Upload() throws android.os.RemoteException;
public String getBehaviorVersion() throws android.os.RemoteException;
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException;
}
