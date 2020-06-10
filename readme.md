# Istio retry demo

The application graph:

![App graph](./network.png?raw=true)

To apply configuration of Istio Retry run the following commands:

```
oc apply -f ./Gateway.yml

oc apply -f ./retry-endpoint/Deployment.yml
oc apply -f ./retry-endpoint/Service.yml
oc apply -f ./retry-endpoint/VirtualService.yml

oc apply -f ./retry-intermediary/Deployment.yml
oc apply -f ./retry-intermediary/Service.yml
oc apply -f ./retry-intermediary/VirtualService.yml
```