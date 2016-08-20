/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ayo.jlog;


import android.content.Context;

import org.ayo.jlog.constant.JLogLevel;
import org.ayo.jlog.constant.JLogSegment;
import org.ayo.jlog.constant.JZoneOffset;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置.
 */
public class JSettings {

    private Context mContext;
    /** DEBUG模式. */
    private boolean mIsDebug;
    /** 字符集. */
    private String mCharset;
    /** 时间格式. */
    private String mTimeFormat;
    /** 时区偏移时间. */
    private JZoneOffset mZoneOffset;
    /** 日志保存的目录. */
    private String mLogDir;
    /** 日志文件的前缀. */
    private String mLogPrefix;
    /** 切片间隔，单位小时. */
    private JLogSegment mLogSegment;
    /** 日志是否记录到文件中. */
    private boolean mWriteToFile;
    /** 写入文件的日志级别. */
    private List<JLogLevel> mLogLevelsForFile;

    public JSettings() {
        mIsDebug = true;
        mCharset = "UTF-8";
        mTimeFormat = "yyyy-MM-dd HH:mm:ss";
        mZoneOffset = JZoneOffset.P0800;
        mLogDir = "";
        mLogPrefix = "";
        mLogSegment = JLogSegment.TWENTY_FOUR_HOURS;
        mWriteToFile = false;
        mLogLevelsForFile = new ArrayList<>();
        mLogLevelsForFile.add(JLogLevel.ERROR);
        mLogLevelsForFile.add(JLogLevel.WTF);
    }

    public Context getContext() {
        return mContext;
    }

    public JSettings setContext(Context context) {
        mContext = context;
        return this;
    }

    public String getCharset() {
        return mCharset;
    }

    public JSettings setCharset(String charset) {
        mCharset = charset;
        return this;
    }

    public String getTimeFormat() {
        return mTimeFormat;
    }

    public JSettings setTimeFormat(String timeFormat) {
        mTimeFormat = timeFormat;
        return this;
    }

    public JZoneOffset getZoneOffset() {
        return mZoneOffset;
    }

    public JSettings setZoneOffset(JZoneOffset zoneOffset) {
        mZoneOffset = zoneOffset;
        return this;
    }

    public String getLogDir() {
        return mLogDir;
    }

    public JSettings setLogDir(String logDir) {
        mLogDir = logDir;
        return this;
    }

    public String getLogPrefix() {
        return mLogPrefix;
    }

    public JSettings setLogPrefix(String logPrefix) {
        mLogPrefix = logPrefix;
        return this;
    }

    public JLogSegment getLogSegment() {
        return mLogSegment;
    }

    public JSettings setLogSegment(JLogSegment logSegment) {
        mLogSegment = logSegment;
        return this;
    }

    public boolean isWriteToFile() {
        return mWriteToFile;
    }

    public JSettings writeToFile(boolean isWriteToFile) {
        mWriteToFile = isWriteToFile;
        return this;
    }

    public List<JLogLevel> getLogLevelsForFile() {
        return mLogLevelsForFile;
    }

    public JSettings setLogLevelsForFile(List<JLogLevel> logLevelsForFile) {
        mLogLevelsForFile = logLevelsForFile;
        return this;
    }

    public boolean isDebug() {
        return mIsDebug;
    }

    public JSettings setDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }
}