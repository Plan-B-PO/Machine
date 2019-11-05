FROM alpine
HEALTHCHECK --interval=5m --timeout=3s \
              CMD curl -f http://localhost:1234/debug || exit 1
CMD sh bash