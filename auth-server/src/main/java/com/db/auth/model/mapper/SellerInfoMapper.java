package com.db.auth.model.mapper;

import com.db.auth.configuration.GlobalMapperConfig;
import com.db.auth.database.repository.UserRepository;
import com.db.auth.exception.ObjectMappingException;
import com.db.auth.model.dto.crud.SellerInfoDto;
import com.db.auth.model.entity.SellerInfo;
import com.db.auth.model.entity.User;
import com.db.auth.utility.cryptographic.JksUtility;
import com.db.auth.utility.cryptographic.PkiUtility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Mapper(config = GlobalMapperConfig.class)
public abstract class SellerInfoMapper implements BaseCrudMapper<SellerInfo, SellerInfoDto> {

    private UserRepository userRepository;

    @Override
    @Mapping(target = "iban", source = "iban", qualifiedByName = "ibanEncryptor", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="user", source = "userId", qualifiedByName = "mapToUser")
    public abstract SellerInfo toEntity(SellerInfoDto dto);

    @Override
    @Mapping(target = "iban", source = "iban", qualifiedByName = "ibanDecrypter", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="userId", source = "user", qualifiedByName = "mapToUserId")
    public abstract SellerInfoDto toDto(SellerInfo entity);

    @Named("ibanDecrypter")
    public String decryptIban(String iban) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return PkiUtility.decrypt(iban, retrieveEncryptionKey());
    }

    @Named("ibanEncryptor")
    public String encryptIban(String iban) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return PkiUtility.encrypt(iban, retrieveEncryptionKey());
    }

    public Key retrieveEncryptionKey() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = JksUtility.getKeyStore(JksUtility.KeyStoreType.JCEKS, "FPSBA-server.jceks", "123456");
        return JksUtility.getKey(keyStore, "FPSBA-encryption", "123456");
    }

    @Named("mapToUserId")
    public long mapToUserId(User user) {
        return user.getId();
    }

    @Named("mapToUser")
    public User mapToUser(long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ObjectMappingException("user does not exist"));
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
