package com.home.homebirthdaytip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId;
import com.home.homebirthdaytip.domain.CCommonPushUsers;
import com.home.homebirthdaytip.service.CCommonPushAccountRetativeIdService;
import com.home.homebirthdaytip.service.CCommonPushUsersService;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HomebirthdaytipApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CCommonPushAccountRetativeIdService cCommonPushAccountRetativeIdService;
    @Autowired
    private CCommonPushUsersService cCommonPushUsersService;

    @Test
    void test(){
        List<CCommonPushUsers> emailAccounts = cCommonPushUsersService.getEmailAccounts();
        List<CCommonPushAccountRetativeId> cCommonPushAccountRetativeIds = new ArrayList<>();
        for (CCommonPushUsers c : emailAccounts) {
            CCommonPushAccountRetativeId relative = new CCommonPushAccountRetativeId();
            relative.setEmailaccount(c.getId());
            CCommonPushUsers db = cCommonPushUsersService.getBaseMapper().selectOne(new QueryWrapper<CCommonPushUsers>().eq("type",2)
                    .eq("name",c.getName()) );
            if(db != null){
                relative.setMessageaccount(db.getId());
            }
            cCommonPushAccountRetativeIds.add(relative);
        }
        cCommonPushAccountRetativeIdService.saveBatch(cCommonPushAccountRetativeIds);
    }

    @Test
    void contextLoads() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");          // 加密的算法，这个算法是默认的
        config.setPassword("");
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "11111";         //自己的密码
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }


    @Test
    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = "12345=";   //加密后的密码
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }
}
