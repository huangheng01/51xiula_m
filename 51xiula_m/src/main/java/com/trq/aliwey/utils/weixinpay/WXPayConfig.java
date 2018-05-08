package com.trq.aliwey.utils.weixinpay;
import java.io.InputStream;

public abstract class WXPayConfig {



    /**
     * ��ȡ App ID
     *
     * @return App ID
     */
    abstract String getAppID();


    /**
     * ��ȡ Mch ID
     *
     * @return Mch ID
     */
    abstract String getMchID();


    /**
     * ��ȡ API ��Կ
     *
     * @return API��Կ
     */
    abstract String getKey();


    /**
     * ��ȡ�̻�֤������
     *
     * @return �̻�֤������
     */
    abstract InputStream getCertStream();

    /**
     * HTTP(S) ���ӳ�ʱʱ�䣬��λ����
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) ����ݳ�ʱʱ�䣬��λ����
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8*1000;
    }

    /**
     * ��ȡWXPayDomain, ���ڶ����������Զ��л�
     * @return
     */
    abstract IWXPayDomain getWXPayDomain();

    /**
     * �Ƿ��Զ��ϱ���
     * ��Ҫ�ر��Զ��ϱ���������ʵ�ָú���� false ���ɡ�
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * ���н����ϱ����̵߳�����
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * �����ϱ�������Ϣ����������������߳�ȥ�����ϱ�
     * ���Լ��㣺����һ����Ϣ200B��10000��Ϣռ�ÿռ� 2000 KB��ԼΪ2MB�����Խ���
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * �����ϱ���һ������ϱ�������
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

}
