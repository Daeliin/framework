package com.blebail.components.cms.country;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CountryService {

    private final Map<String, Country> countryByCode;
    private final CountryRepository repository;
    private final CountryConversion conversion;

    @Inject
    public CountryService(CountryRepository repository) {
        this.repository = repository;
        this.conversion = new CountryConversion();
        this.countryByCode = new ConcurrentHashMap<>();
    }

    public Collection<Country> findAll() {
        if (countryByCode.isEmpty()) {
            invalidateCache();
        }

        return new TreeSet<>(countryByCode.values());
    }

    public Country findByCode(String countryCode) {
        if (countryByCode.isEmpty()) {
            invalidateCache();
        }

        if (Strings.isNullOrEmpty(countryCode) || !countryByCode.containsKey(countryCode)) {
            throw new NoSuchElementException(String.format("There is no country for country code %s", countryCode));
        }

        return countryByCode.get(countryCode);
    }

    public void invalidateCache() {
        countryByCode.clear();

        repository.findAll()
                .stream()
                .map(conversion::from)
                .forEach(country -> countryByCode.put(country.code, country));
    }
}
