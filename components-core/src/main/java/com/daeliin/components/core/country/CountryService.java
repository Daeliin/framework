package com.daeliin.components.core.country;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class CountryService {

    private final Map<String, Country> countryByCode;
    private final CountryRepository repository;
    private final CountryConversion conversion;

    @Inject
    public CountryService(CountryRepository repository) {
        this.repository = repository;
        this.conversion = new CountryConversion();
        this.countryByCode = new ConcurrentHashMap<>();
    }

    public Country findByCode(String countryCode) {
        if (countryByCode.isEmpty()) {
            invalidateCache();
        }

        if (Strings.isNullOrEmpty(countryCode) || !countryByCode.containsKey(countryCode)) {
            throw new PersistentResourceNotFoundException(String.format("There is no country for country code %s", countryCode));
        }

        return countryByCode.get(countryCode);
    }

    private void invalidateCache() {
        countryByCode.clear();

        repository.findAll()
                .stream()
                .map(conversion::instantiate)
                .forEach(country -> countryByCode.put(country.code, country));
    }
}
