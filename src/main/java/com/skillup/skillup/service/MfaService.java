package com.skillup.skillup.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
public class MfaService {

    /**
     * Genera una clave secreta aleatoria de 32 caracteres para el usuario.
     */
    public String generateSecret() {
        return new DefaultSecretGenerator().generate();
    }

    /**
     * Genera un URI de datos (base64) con la imagen PNG del código QR,
     * lista para poner en un <img src="...">.
     */
    public String generateQrImageUri(String secret, String correo) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(correo)
                .secret(secret)
                .issuer("SkillUp")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);

        return getDataUriForImage(imageData, generator.getImageMimeType());
    }

    /**
     * Valida si el código TOTP de 6 dígitos ingresado por el usuario es correcto.
     */
    public boolean isCodeValid(String secret, String code) {
        DefaultCodeVerifier verifier = new DefaultCodeVerifier(
                new DefaultCodeGenerator(),
                new SystemTimeProvider()
        );
        // Al usar setAllowedTimePeriodDiscrepancy(2) permitimos códigos de 2 periodos atrás
        // y 2 periodos adelante (±60s). Esto soluciona problemas de hora en el celular.
        verifier.setAllowedTimePeriodDiscrepancy(2);
        return verifier.isValidCode(secret, code);
    }
}
