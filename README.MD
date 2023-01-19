# dependency

```access transformers
        <repository>
            <id>nealxyc-github-repo</id>
            <url>https://raw.githubusercontent.com/nealxyc/mvn-repo/master/</url>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>daily</updatePolicy>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
```

```access transformers
        <dependency>
            <groupId>com.lairon.plugins</groupId>
            <artifactId>0xItemStackUtils</artifactId>
            <version>1.6.5-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
```