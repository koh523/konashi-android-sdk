package com.uxxu.konashi.lib.action;

import android.bluetooth.BluetoothGattService;

import com.uxxu.konashi.lib.KonashiUUID;
import com.uxxu.konashi.lib.store.I2cStore;
import com.uxxu.konashi.lib.util.I2cUtils;

import java.util.UUID;

/**
 * Created by izumin on 9/21/15.
 */
public class I2cWriteAction extends I2cAction {

    private static final UUID UUID = KonashiUUID.I2C_WRITE_UUID;

    private final byte mAddress;
    private final byte[] mData;

    public I2cWriteAction(BluetoothGattService service, byte address, byte[] data, I2cStore store) {
        super(service, UUID, store, false);
        mAddress = address;
        mData = data;
    }

    @Override
    protected void setValue() {
        byte[] value = new byte[20];
        value[0] = (byte) mData.length;
        value[1] = (byte) ((mAddress << 1) & 0xfe);
        System.arraycopy(mData, 0, value, 2, mData.length);
        getCharacteristic().setValue(value);
    }

    @Override
    protected boolean hasValidParams() {
        return I2cUtils.isValidDataLength(mData.length);
    }
}